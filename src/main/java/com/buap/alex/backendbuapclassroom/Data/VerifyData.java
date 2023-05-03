package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.CursoRepository;
import com.buap.alex.backendbuapclassroom.Repository.TareaRepository;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VerifyData {

    final CursoRepository cursoRepository;

    final TareaRepository tareaRepository;

    final UserRepository userRepository;

    public VerifyData(CursoRepository cursoRepository, TareaRepository tareaRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.tareaRepository = tareaRepository;
        this.userRepository = userRepository;
    }

    public Curso verifyCurso(long id){
        Optional<Curso> curso = cursoRepository.findCursoByIdCurso(id);
        if (!curso.isPresent()) {
            throw new ResourceNotFoundException("Not foun class");
        }
        return curso.get();
    }

    public Tarea verifyTarea(long id) {
        Optional<Tarea> tarea = tareaRepository.findTareaByIdTarea(id);
        if (!tarea.isPresent()) {
            throw new ResourceNotFoundException("Tarea not found");
        }
        return tarea.get();
    }

    public User verifyUserByMatricula(long matricula) {
        Optional<User> user = userRepository.findUserByMatricula(matricula);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el alumno con matricula " + matricula + " intenta de nuevo");
        }
        return user.get();
    }


    //Función para validar que existe un usuario por su ID
    public User verifyUserByID(long id) {
        Optional<User> user = userRepository.findUserByIdUser(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }
}
