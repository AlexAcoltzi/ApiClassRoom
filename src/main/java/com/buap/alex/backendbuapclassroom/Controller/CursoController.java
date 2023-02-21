package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping("/Cursos")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;

    @PostMapping("/NewCurso")
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso){
        String ruta = createDirectory(curso.getNombreCurso(), curso.getNRC());
        curso.setRuta(ruta);
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/updateCurso")
    public ResponseEntity<Curso> updateCurso(@RequestBody Curso cursoData, @RequestParam long NRC){
        Optional<Curso> curso = cursoRepository.findCursoByNRC(NRC);
        Curso curso1 = curso.get();
        curso1.setNombreCurso(cursoData.getNombreCurso());
        curso1.setDiasCurso(cursoData.getDiasCurso());
        curso1.setHorarioCurso(cursoData.getHorarioCurso());
        cursoRepository.save(curso1);
        return ResponseEntity.ok(curso1);
    }

    @GetMapping("/getCurso")
    public ResponseEntity<?> getCurso(@RequestParam long NRC){
        return new ResponseEntity<>(verifyCurso(NRC), HttpStatus.OK);
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
        Optional<Curso> curso = cursoRepository.findCursoByNRC(NRC);
        if (!curso.isPresent()){
            throw new ResourceNotFoundException("Not found class");
        }
        return curso.get();
    }

}
