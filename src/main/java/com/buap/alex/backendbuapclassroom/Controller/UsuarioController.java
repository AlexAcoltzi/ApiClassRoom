package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.buap.alex.backendbuapclassroom.Data.VerifyData;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {

    final
    UserRepository userRepository;
    final
    VerifyData verifyData;
    final CursoRepository cursoRepository;

    public UsuarioController(UserRepository userRepository, VerifyData verifyData, CursoRepository cursoRepository) {
        this.userRepository = userRepository;
        this.verifyData = verifyData;
        this.cursoRepository = cursoRepository;
    }


    //Función para validar que existe el usuario por matrícula

    //Servicio para crear un usuario
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userRepository.existsUserByCorreoOrMatricula(user.getCorreo(), user.getMatricula())) {
            throw new ResourceNotFoundException("Ya existe el usuario con esa matricula o ese correo");
        }
        String contraDecode = Base64.getEncoder().encodeToString(user.getContrasena().getBytes());
        System.out.println(contraDecode);
        user.setContrasena(contraDecode);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //Servicio para buscar un usuario por su matrícula
    @GetMapping("/getByMat")
    public ResponseEntity<?> getUserByMat(@RequestParam long matricula) throws JsonProcessingException {
        User user = verifyData.verifyUserByMatricula(matricula);
        String user1 = new ObjectMapper().writerWithView(JsonViewProfiles.User.class).writeValueAsString(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }


    //Servicio para buscar un usuario por su id
    @GetMapping("/getByID")
    public ResponseEntity<?> getUserByID(@RequestParam long id) throws JsonProcessingException {
        User user = verifyData.verifyUserByID(id);
        String dataUser = new ObjectMapper().writerWithView(JsonViewProfiles.User.class).writeValueAsString(user);

        return new ResponseEntity<>(dataUser, HttpStatus.OK);

    }


    //Servicio para validar la autenticación de un usuario
    @GetMapping("/getAcces")
    public ResponseEntity<?> getAcces(@RequestParam long matricula, @RequestParam String contrasena) throws JsonProcessingException {
        User user = verifyData.verifyUserByMatricula(matricula);
        String contraEncode = new String(Base64.getDecoder().decode(contrasena), StandardCharsets.UTF_8);
        String contraUser = new String(Base64.getDecoder().decode(user.getContrasena()), StandardCharsets.UTF_8);
        System.out.println(contraUser);
        if (!contraUser.equals(contraEncode)) {
            throw new ResourceNotFoundException("Contraseña incorrecta");
        }
        String user1 = new ObjectMapper().writerWithView(JsonViewProfiles.User.class).writeValueAsString(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }


    //Servicio para cambiar la contraseña de un usuario
    @PutMapping("/updatePass")
    public ResponseEntity<?> updateUser(@RequestParam String contrasena, @RequestParam long matricula) {
        User user1 = verifyData.verifyUserByMatricula(matricula);
        user1.setContrasena(contrasena);
        userRepository.save(user1);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Servicio para obtener los cursos de un maestro
    @GetMapping("/getCursosMa")
    public ResponseEntity<?> getCursosMa(@RequestParam long idUser) throws JsonProcessingException {
        Optional<User> user = userRepository.findUserByIdUser(idUser);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("El usuario no se ha encontrado");
        }
        List<Curso> cursos = user.get().getCursosMaestros();
        String cursosList = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class).writeValueAsString(cursos);

        return new ResponseEntity<>(cursosList, HttpStatus.OK);
    }


    //Servicio para cambiar la foto de perfil de un usuario
    @PutMapping("/updatePhoto")
    public ResponseEntity<?> updateFoto(@RequestParam String dataBase64, @RequestParam long id) {
        System.out.println(dataBase64);
        if (dataBase64 == null || dataBase64.isEmpty()){
            throw new IllegalArgumentException("La cadena decode es nula o vacía");
        }
        byte[] encode = Base64.getDecoder().decode(dataBase64);
        User user = verifyData.verifyUserByID(id);
        File file = new File(createRuta(user.getNombre()).concat(".jpg"));
        try(OutputStream outputStream= new FileOutputStream(file)) {
            outputStream.write(encode);
            user.setRuta(file.getAbsolutePath());
        } catch (IOException e){
            throw new ResourceNotFoundException("No se pudo lograr el guardado de imagen");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Servicio para eliminar a un usuario
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam long idUser) {
        User user = verifyData.verifyUserByID(idUser);
        Set<Curso> cursos = new HashSet<>();
        cursos.addAll(cursoRepository.findCursosByAlumnos(user));
        cursos.addAll(cursoRepository.findCursosByMaestro(user));
        for (Curso curso : cursos) {
            curso.removeUser(user);
        }
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected String createRuta(String nombre){
        String PATH = "../../users/".concat(nombre);
        File directory = new File(PATH);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directorio creado " + PATH);
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        return PATH;
    }
}
