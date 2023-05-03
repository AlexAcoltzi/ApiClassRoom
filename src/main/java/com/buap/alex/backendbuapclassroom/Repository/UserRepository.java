package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByMatricula(Long matricula);

    boolean existsUserByCorreoOrMatricula(String correo, long matricula);

    Optional<User> findUserByIdUser(long id);

    List<User> findUsersByCursos(Curso curso);

    List<User> findUserByCursosMaestros(Curso curso);

    Optional<User> findUserByComentarios(Comentario comentario);

    Optional<User> findUserByAlumnoTarea(AlumnoTarea alumnoTarea);

    Optional<User> findUserByArchivos(Archivo archivo);
}
