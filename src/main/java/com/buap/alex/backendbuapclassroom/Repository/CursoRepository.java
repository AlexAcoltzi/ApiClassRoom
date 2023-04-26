package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import jakarta.websocket.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

    Optional<Curso> findCursoByNrc(long NRC);

    Optional<Curso> findCursoByIdCurso(long idCurso);


    @Query(value = "SELECT DISTINCT u from Curso u JOIN u.alumnos c JOIN u.maestro cm WHERE c= :user or cm =: user")
    List<Curso> findCursosByAlumnosOrMaestro(@Param("user") User user);


    /*List<Curso> findCursosByAlumnos(User user);

    List<Curso> findCursosByMaestro(User user);*/

    Optional<Curso> findCursoByTareasContains(Tarea tarea);

}
