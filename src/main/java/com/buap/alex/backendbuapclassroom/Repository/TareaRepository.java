package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends CrudRepository<Tarea, Long> {

    Optional<Tarea> findTareaByIdTarea(long id);

    Optional<Tarea> findTareaByAlumnoTareas(AlumnoTarea alumnoTarea);
}
