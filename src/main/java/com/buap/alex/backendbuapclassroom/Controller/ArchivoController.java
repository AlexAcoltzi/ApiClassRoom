package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.FileToSave;
import com.buap.alex.backendbuapclassroom.Data.VerifyData;
import com.buap.alex.backendbuapclassroom.Domain.Archivo;
import com.buap.alex.backendbuapclassroom.Domain.Curso;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Repository.ArchivoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/Archivo")
public class ArchivoController {

    final VerifyData verifyData;
    final
    ArchivoRepository archivoRepository;

    public ArchivoController(VerifyData verifyData, ArchivoRepository archivoRepository) {
        this.verifyData = verifyData;
        this.archivoRepository = archivoRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> updateArchivo(@RequestBody FileToSave file){
        Archivo archivo = file.getArchivo();
        User user = verifyData.verifyUserByID(file.getIdUser());
        Curso curso = verifyData.verifyCurso(file.getIdClase());
        String ruta = curso.getRuta();
        try {
            byte[] archivoBytes = Base64.getDecoder().decode(file.getCode64());
            String nombre = archivo.getNombre().concat(".".concat(file.getType()));
            String rutaToSave = ruta.concat("/".concat(nombre));
            FileOutputStream outputStream = new FileOutputStream(rutaToSave);
            outputStream.write(archivoBytes);
            outputStream.close();
            archivo.addAutor(user);
            archivo.addCursos(curso);
            archivo.setRuta(rutaToSave);
            archivoRepository.save(archivo);
            return ResponseEntity.ok("Archivo Guardado");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un problema al guardar el archivo");
        }
    }


}
