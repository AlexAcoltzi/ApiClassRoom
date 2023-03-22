package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping("/Tarea")
public class TareaController {

    @Autowired
    TareaRepository repository;
    CursoRepository cursoRepository;

    @PostMapping("/crearTarea")
    public ResponseEntity<?> crearTarea(@RequestBody Tarea tarea, @RequestParam  Long nrc){
        Curso curso= cursoRepository.findCursoByNrc(nrc).get();
        String Ruta = createDirectory(curso.getRuta(), tarea.getNombre());
        tarea.setRuta(Ruta);
        repository.save(tarea);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    protected static String createDirectory(String ruta, String name){
        String PATH = ruta;
        String directoryName = PATH.concat(" " + name);


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
}
