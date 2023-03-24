package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
   Optional<User> findUserByMatricula(Long matricula);

   boolean existsUserByCorreoOrMatricula(String correo, long matricula);

   Optional<User> findUserByIdUser(long id);
}
