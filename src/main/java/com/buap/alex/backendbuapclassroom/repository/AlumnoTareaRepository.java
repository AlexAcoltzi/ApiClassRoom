package com.buap.alex.backendbuapclassroom.repository;

import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnoTareaRepository extends CrudRepository<AlumnoTarea, Long> {
    Optional<AlumnoTarea> findAlumnoTareaByTarea(Tarea tarea);
}
