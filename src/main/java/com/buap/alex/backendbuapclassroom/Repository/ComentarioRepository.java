package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.Comentario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
    Optional<Comentario> findComentarioByIdComent(long id);
}
