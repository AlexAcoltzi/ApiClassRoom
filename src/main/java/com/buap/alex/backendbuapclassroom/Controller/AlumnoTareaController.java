package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.TareaToQualify;
import com.buap.alex.backendbuapclassroom.Data.TareaToSave;
import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.AlumnoTareaRepository;
import com.buap.alex.backendbuapclassroom.Repository.TareaRepository;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/alumnoTarea")
public class AlumnoTareaController {

    final
    AlumnoTareaRepository alumnoTareaRepository;
    final
    UserRepository userRepository;
    final TareaRepository tareaRepository;

    public AlumnoTareaController(AlumnoTareaRepository alumnoTareaRepository, UserRepository userRepository, TareaRepository tareaRepository) {
        this.alumnoTareaRepository = alumnoTareaRepository;
        this.userRepository = userRepository;
        this.tareaRepository = tareaRepository;
    }

    @PostMapping("/guardarTarea")
    public ResponseEntity<?> saveTarea(@RequestBody TareaToSave alumnoTarea){
        AlumnoTarea alumnoTarea1 = alumnoTarea.getAlumnoTarea();
        Tarea tarea = verifyTarea(alumnoTarea.getIdTarea());
        User user = verifyUser(alumnoTarea.getIdUser());
        alumnoTarea1.addTarea(tarea);
        alumnoTarea1.addUser(user);
        alumnoTareaRepository.save(alumnoTarea1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/calificar")
    public ResponseEntity<?> calificar( @RequestBody TareaToQualify tarea){
        AlumnoTarea alumnoTarea = verifyAlumTarea(tarea.getIdTarea());
        alumnoTarea.calificar(tarea.getCalificacion());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminar(@RequestParam long idTarea){
        AlumnoTarea alumnoTarea = verifyAlumTarea(idTarea);
        User user = userRepository.findUserByAlumnoTarea(alumnoTarea).get();
        Tarea tarea = tareaRepository.findTareaByAlumnoTareas(alumnoTarea).get();
        alumnoTarea.removeTarea(tarea);
        alumnoTarea.removeUser(user);
        alumnoTareaRepository.delete(alumnoTarea);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected Tarea verifyTarea(long id){
        Optional<Tarea> tarea = tareaRepository.findTareaByIdTarea(id);
        if (tarea.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el curso");
        }
        return tarea.get();
    }

    protected User verifyUser(long id){
        Optional<User> user = userRepository.findUserByIdUser(id);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el usuario");
        }
        return user.get();
    }
    protected AlumnoTarea verifyAlumTarea(long id){
        Optional<AlumnoTarea> alumnoTarea = alumnoTareaRepository.findAlumnoTareaByIdAlumnoTarea(id);
        if (alumnoTarea.isEmpty()){
            throw new ResourceNotFoundException("No se encontró la tarea");
        } return alumnoTarea.get();
    }

}
