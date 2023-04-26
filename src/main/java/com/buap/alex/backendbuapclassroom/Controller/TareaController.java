package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.buap.alex.backendbuapclassroom.Data.ModificarTarea;
import com.buap.alex.backendbuapclassroom.Data.TareaNueva;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.Repository.TareaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    CursoRepository cursoRepository;


    //Servicio para crear una tarea
    @PostMapping("/create")
    public ResponseEntity<?> crearTarea(@RequestBody TareaNueva tarea) {
        Curso curso = verifyCursoById(tarea.getIdCurso());
        Tarea tarea1 = tarea.getTarea();
        String Ruta = createDirectory(curso.getRuta(), tarea1.getNombre());
        tarea1.setRuta(Ruta);
        repository.save(tarea1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //Servicio para modificar una tarea
    @PutMapping("/update")
    public ResponseEntity<?> updateTarea(@RequestBody ModificarTarea tarea) {
        Tarea tareaToUpdate = verifyTarea(tarea.getIdTarea());
        Tarea dataTarea = tarea.getTarea();

        tareaToUpdate.setDescripcion(dataTarea.getDescripcion());
        tareaToUpdate.setFechaLimite(dataTarea.getFechaLimite());
        tareaToUpdate.setHoraLimite(dataTarea.getHoraLimite());

        repository.save(tareaToUpdate);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Servicio para obtener una tarea
    @GetMapping("/get")
    public ResponseEntity<?> getTarea(@RequestParam long id) throws JsonProcessingException {
        Tarea tarea = verifyTarea(id);

        String tareaToGet = new ObjectMapper().writerWithView(JsonViewProfiles.Tarea.class).writeValueAsString(tarea);

        return new ResponseEntity<>(tareaToGet, HttpStatus.OK);
    }


    //Servicio para eliminar una tarea
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTarea(@RequestParam long id) {
        Tarea tarea = verifyTarea(id);
        Curso curso = cursoRepository.findCursoByTareasContains(tarea).get();
        tarea.removeCurso(curso);
        repository.delete(tarea);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Función para validar que existe el curso buscado por su ID
    protected Curso verifyCursoById(long id) {
        Optional<Curso> curso = cursoRepository.findCursoByIdCurso(id);
        if (!curso.isPresent()) {
            throw new ResourceNotFoundException("Not foun class");
        }
        return curso.get();
    }


    //Función para validar si una tarea existe
    protected Tarea verifyTarea(long id) {
        Optional<Tarea> tarea = repository.findTareaByIdTarea(id);
        if (!tarea.isPresent()) {
            throw new ResourceNotFoundException("Tarea not found");
        }
        return tarea.get();
    }


    protected static String createDirectory(String ruta, String name) {
        String directoryName = ruta.concat("/" + name);

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
}
