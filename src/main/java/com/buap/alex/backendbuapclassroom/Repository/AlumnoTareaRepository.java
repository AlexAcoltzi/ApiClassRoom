package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnoTareaRepository extends CrudRepository<AlumnoTarea, Long> {
    Optional<AlumnoTarea> findAlumnoTareaByIdAlumnoTarea(long id);
}
