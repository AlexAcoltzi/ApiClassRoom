package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Cursos")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;

    @PostMapping("/NewCurso")
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso){
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("updateCurso")
    public ResponseEntity<Curso> updateCurso(@RequestBody Curso cursoData, @RequestParam long NRC){
        Optional<Curso> curso = cursoRepository.findCursoByNRC(NRC);
        Curso curso1 = curso.get();
        curso1.setNombreCurso(cursoData.getNombreCurso());
        curso1.setDiasCurso(cursoData.getDiasCurso());
        curso1.setHorarioCurso(cursoData.getHorarioCurso());
        cursoRepository.save(curso1);
        return ResponseEntity.ok(curso1);
    }

}
