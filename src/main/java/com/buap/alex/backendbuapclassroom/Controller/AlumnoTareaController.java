package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.repository.AlumnoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/alumnoTarea")
public class AlumnoTareaController {

    @Autowired
    AlumnoTareaRepository tareaRepository;

    protected AlumnoTarea verifyTarea(Tarea tarea){
        Optional<AlumnoTarea> tarea1 = tareaRepository.findAlumnoTareaByTarea(tarea);
        if (!tarea1.isPresent()){
            throw  new ResourceNotFoundException("No se encontro la tarea");
        }
        return tarea1.get();
    }

    @PostMapping("/guardarTarea")
    public ResponseEntity<?> saveTarea(@RequestBody AlumnoTarea alumnoTarea){
        tareaRepository.save(alumnoTarea);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/calificar")
    public ResponseEntity<?> calificar(@RequestParam int calificacion, @RequestBody Tarea tarea){
        AlumnoTarea alumnoTarea = verifyTarea(tarea);
        alumnoTarea.setCalificacion(calificacion);
        tareaRepository.save(alumnoTarea);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*protected static String CerarRutaTarea(String ruta, String autor){

    }*/

}
