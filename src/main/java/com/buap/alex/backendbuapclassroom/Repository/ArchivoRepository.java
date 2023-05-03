package com.buap.alex.backendbuapclassroom.Repository;

import com.buap.alex.backendbuapclassroom.Domain.Archivo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArchivoRepository extends CrudRepository<Archivo, Long> {
    Optional<Archivo> findArchivoByIdArchivo(long id);
}
