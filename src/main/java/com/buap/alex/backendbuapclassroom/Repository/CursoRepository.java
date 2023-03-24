package com.buap.alex.backendbuapclassroom.repository;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
    public Optional<Curso> findCursoByNRC(long NRC);
}
