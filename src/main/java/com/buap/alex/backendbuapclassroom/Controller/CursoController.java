package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.CursoMaestro;
import com.buap.alex.backendbuapclassroom.Data.DataCursos;
import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Cursos")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/NewCurso")
    public ResponseEntity<?> crearCurso(@RequestBody CursoMaestro cursoMaestro){
        Curso curso = cursoMaestro.getCurso();
        User user = userRepository.findUserByIdUser(cursoMaestro.getId()).get();
        if (user.getTipo() != 0){
            throw new ResourceNotFoundException("El usuario no es un maestro");
        }
        String ruta = createDirectory(curso.getNombre(), curso.getNrc());
        curso.setRuta(ruta);
        curso.setMaestro(user);
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getCursosMa")
    public ResponseEntity<?> getCursosMa(@RequestParam long idUser) throws JsonProcessingException {
        Optional<User> user = userRepository.findUserByIdUser(idUser);
        if (!user.isPresent()){
            throw new ResourceNotFoundException("El usuario no se ha encontrado");
        }
        List<Curso> cursos = user.get().getCursosMaestros();
        String cursosList = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class).writeValueAsString(cursos);

        return new ResponseEntity<>(cursosList, HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<?> getComentarios(@RequestParam long nrc) throws JsonProcessingException {
        Curso curso = verifyCurso(nrc);
        DataCursos dataCursos = new DataCursos(curso.getAlumnos(), curso.getComentarios(),
                curso.getArchivos(), curso.getTareas());

        String datosCurso = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class)
                .writeValueAsString(dataCursos);
        return new ResponseEntity<>(datosCurso, HttpStatus.OK);
    }

    @PutMapping("/updateCurso")
    public ResponseEntity<Curso> updateCurso(@RequestBody Curso cursoData, @RequestParam long NRC){
        Optional<Curso> curso = cursoRepository.findCursoByNrc(NRC);
        Curso curso1 = curso.get();
        curso1.setNombre(cursoData.getNombre());
        curso1.setDias(cursoData.getDias());
        curso1.setHorario(cursoData.getHorario());
        cursoRepository.save(curso1);
        return ResponseEntity.ok(curso1);
    }

    @GetMapping("/getCurso")
    public ResponseEntity<?> getCurso(@RequestParam long NRC) throws JsonProcessingException {
        Curso curso = verifyCurso(NRC);
        String cursoRet = new ObjectMapper().writerWithView(JsonViewProfiles.Curso.class).writeValueAsString(curso);
        return new ResponseEntity<>(cursoRet, HttpStatus.OK);
    }


    //Función para agregar un alumno a una clase
    @PostMapping("/AgregarAlumno")
    public ResponseEntity<?> agregarAlumno(@RequestParam long idUser, @RequestParam long idCurso){
        Optional<Curso> curso = cursoRepository.findById(idCurso);
        Optional<User> user = userRepository.findUserByIdUser(idUser);
        if (!curso.isPresent() || !user.isPresent()){
            throw new ResourceNotFoundException("no existe el usuario o la clase");
        }
        List<User> listU = curso.get().getAlumnos();
        List<Curso> listC = user.get().getCursos();
        listU.add(user.get());
        listC.add(curso.get());
        cursoRepository.save(curso.get());
        userRepository.save(user.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static String createDirectory(String name, long NRC){
        String PATH = "../../Cursos/";
        String directoryName = PATH.concat(name.concat("/".concat(String.valueOf(NRC))));


        File directory = new File(directoryName);
        if (!directory.exists()){
            if (directory.mkdirs()){
                System.out.println("Directorio creado " + directoryName);
            } else{
                System.out.println("Failed to create directory!");
            }
        }
        return directoryName;
    }

    protected Curso verifyCurso(long NRC){
        Optional<Curso> curso = cursoRepository.findCursoByNrc(NRC);
        if (!curso.isPresent()){
            throw new ResourceNotFoundException("Not found class");
        }
        return curso.get();
    }

}
