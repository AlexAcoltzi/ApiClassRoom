package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByMatricula(Long matricula);

    boolean existsUserByCorreoOrMatricula(String correo, long matricula);

    Optional<User> findUserByIdUser(long id);

    @Query("SELECT DISTINCT u FROM User u join u.cursos c join u.cursosMaestros cm where c=:curso OR cm=:curso")
    List<User> findUserByCursosOrCursosMaestros(@Param("curso") Curso curso);

    /*List<User> findUsersByCursos(Curso curso);

    List<User> findUserByCursosMaestros(Curso curso);*/
}
