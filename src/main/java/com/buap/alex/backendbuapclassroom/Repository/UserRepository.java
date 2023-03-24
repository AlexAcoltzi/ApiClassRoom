package com.buap.alex.backendbuapclassroom.repository;

import com.buap.alex.backendbuapclassroom.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    public Optional<User> findUserByMatricula(Long matricula);
}
