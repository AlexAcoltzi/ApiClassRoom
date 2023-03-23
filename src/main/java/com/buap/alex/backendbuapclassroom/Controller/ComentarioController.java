package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.Comentario;
import com.buap.alex.backendbuapclassroom.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    ComentarioRepository comentarioRepository;

    @PostMapping("/crearComentario")
    public ResponseEntity<?> crearComentario(@RequestBody Comentario comentario){
        comentarioRepository.save(comentario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
