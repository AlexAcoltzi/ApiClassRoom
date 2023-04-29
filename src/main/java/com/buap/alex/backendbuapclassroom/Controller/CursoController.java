package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.CursoMaestro;
import com.buap.alex.backendbuapclassroom.Data.DataCursos;
import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.buap.alex.backendbuapclassroom.Data.ModificarCurso;
import com.buap.alex.backendbuapclassroom.Domain.Archivo;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/Cursos")
public class CursoController {

    final
    CursoRepository cursoRepository;
    final
    UserRepository userRepository;

    public CursoController(CursoRepository cursoRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.userRepository = userRepository;
    }


    //Servicio para crear un curso
    @PostMapping("/create")
    public ResponseEntity<?> crearCurso(@RequestBody CursoMaestro cursoMaestro) {
        Curso curso = cursoMaestro.getCurso();
        User user = verifyUser(cursoMaestro.getId());
        if (user.getTipo() != 0) {
            throw new ResourceNotFoundException("El usuario no es un maestro");
        }
        String ruta = createDirectory(curso.getNombre(), curso.getNrc());
        curso.setRuta(ruta);
        curso.setMaestro(user);
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //Servicio para obtener datos de un curso requerido
    @GetMapping("/getData")
    public ResponseEntity<?> getComentarios(@RequestParam long nrc) throws JsonProcessingException {
        Curso curso = verifyCursoByNrc(nrc);
        DataCursos dataCursos = new DataCursos(curso.getAlumnos(), curso.getComentarios(),
                (List<Archivo>) curso.getArchivos(), curso.getTareas());

        String datosCurso = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class)
                .writeValueAsString(dataCursos);
        return new ResponseEntity<>(datosCurso, HttpStatus.OK);
    }


    //Servicio para actualizar los datos de un curso
    @PutMapping("/update")
    public ResponseEntity<Curso> updateCurso(@RequestBody ModificarCurso modificarCurso) {
        Curso cursoData = modificarCurso.getCurso();
        Curso curso = verifyCursoById(modificarCurso.getIdCurso());

        curso.setDias(cursoData.getDias());
        curso.setHorario(cursoData.getHorario());

        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Servicio para obtener un curso en concreto
    @GetMapping("/get")
    public ResponseEntity<?> getCurso(@RequestParam long NRC) throws JsonProcessingException {
        Curso curso = verifyCursoByNrc(NRC);
        String cursoRet = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class).writeValueAsString(curso);
        return new ResponseEntity<>(cursoRet, HttpStatus.OK);
    }


    //Servicio para agregar un alumno a una clase
    @PutMapping("/addAlumn")
    public ResponseEntity<?> agregarAlumno(@RequestParam long idUser, @RequestParam long idCurso) {
        Curso curso = verifyCursoById(idCurso);
        User user = verifyUser(idUser);
        curso.addUser(user);
        cursoRepository.save(curso);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Servicio para eliminar un curso
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCurso(@RequestParam long idCurso) throws JsonProcessingException {
        Curso curso = verifyCursoById(idCurso);
        Set<User> users = new HashSet<>();
        users.addAll(userRepository.findUsersByCursos(curso));
        users.addAll(userRepository.findUserByCursosMaestros(curso));
        for (User user : users){
            if (user.getTipo() == 1){
                user.removeCursos(curso);
            } else user.removeCursoMa(curso);
        }
        cursoRepository.delete(curso);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Función para Crear el directorio en el que se almacenará los archivos del curso
    private static String createDirectory(String name, long NRC) {
        String PATH = "../../Cursos/";
        String directoryName = PATH.concat(name.concat("/".concat(String.valueOf(NRC))));


        File directory = new File(directoryName);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directorio creado " + directoryName);
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        return directoryName;
    }


    //Función para validar que existe el curso buscado por su NRC
    protected Curso verifyCursoByNrc(long NRC) {
        Optional<Curso> curso = cursoRepository.findCursoByNrc(NRC);
        if (curso.isEmpty()) {
            throw new ResourceNotFoundException("Not found class");
        }
        return curso.get();
    }


    //Función para validar que existe el curso buscado por su ID
    protected Curso verifyCursoById(long id) {
        Optional<Curso> curso = cursoRepository.findCursoByIdCurso(id);
        if (curso.isEmpty()) {
            throw new ResourceNotFoundException("Not foun class");
        }
        return curso.get();
    }


    //Función para validar que existe un usuario buscado
    protected User verifyUser(long ID) {
        Optional<User> user = userRepository.findUserByIdUser(ID);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el alumno con matricula " + ID + " intenta de nuevo");
        }
        return user.get();
    }

}
