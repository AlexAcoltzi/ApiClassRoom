package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.DataComentario;
import com.buap.alex.backendbuapclassroom.Domain.Comentario;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.ComentarioRepository;
import com.buap.alex.backendbuapclassroom.Repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    final ComentarioRepository comentarioRepository;
    final CursoRepository cursoRepository;
    final UserRepository userRepository;

    public ComentarioController(ComentarioRepository comentarioRepository, CursoRepository cursoRepository, UserRepository userRepository) {
        this.comentarioRepository = comentarioRepository;
        this.cursoRepository = cursoRepository;
        this.userRepository = userRepository;

    }

    @PostMapping("/create")
    public ResponseEntity<?> crearComentario(@RequestBody DataComentario comentarioToSave) {
        Comentario comentario = comentarioToSave.getComentario();
        comentario.addCurso(verifyCurso(comentarioToSave.getIdCurso()));
        comentario.addUser(verifyUser(comentarioToSave.getIdUser()));
        comentarioRepository.save(comentario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComentario(@RequestParam long id){
        Comentario comentario = verifyComent(id);
        User user = comentario.getUser();
        List<Curso> cursos = comentario.getCursos();
        comentario.deleteUser(user);
        for (Curso curso : cursos){
            comentario.removeCurso(curso);
        }
        comentarioRepository.delete(comentario);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    protected Curso verifyCurso(long id) {
        Optional<Curso> curso = cursoRepository.findCursoByIdCurso(id);
        if (curso.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el curso ");
        }
        return curso.get();
    }

    protected User verifyUser(long id) {
        Optional<User> user = userRepository.findUserByIdUser(id);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el usuario");
        }
        return user.get();
    }

    protected Comentario verifyComent(long id){
        Optional<Comentario> comentario = comentarioRepository.findComentarioByIdComent(id);
        if (comentario.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el comentario");
        }
        return comentario.get();
    }

}
