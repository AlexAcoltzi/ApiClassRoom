package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

    Optional<Curso> findCursoByNrc(long NRC);

    Optional<Curso> findCursoByIdCurso(long idCurso);


    List<Curso> findCursosByAlumnos(User user);

    List<Curso> findCursosByMaestro(User user);


    Optional<Curso> findCursoByTareasContains(Tarea tarea);

}
