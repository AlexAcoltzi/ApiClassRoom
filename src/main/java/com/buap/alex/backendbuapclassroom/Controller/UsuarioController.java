package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.Exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {

    @Autowired
    UserRepository userRepository;

    protected User verifyUser(long matricula){
        Optional<User> user = userRepository.findUserByMatricula(matricula);
        if (!user.isPresent()){
            throw new ResourceNotFoundException("No se encontró el alumno con matricula " + matricula + " intenta de nuevo");
        }
        return user.get();
    }

    @PostMapping("/NewUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if (userRepository.existsUserByCorreoOrMatricula(user.getCorreo(), user.getMatricula())){
            throw new ResourceNotFoundException("Ya existe el usuario con esa matricula o ese correo");
        }
        String contraDecode = Base64.getEncoder().encodeToString(user.getContrasena().getBytes());
        System.out.println(contraDecode);
        user.setContrasena(contraDecode);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getUserByMat")
    public ResponseEntity<?> getUserByMat(@RequestParam long matricula) throws JsonProcessingException {
        User user = verifyUser(matricula);
        String user1 = new ObjectMapper().writerWithView(JsonViewProfiles.User.class).writeValueAsString(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @GetMapping("/getAcces")
    public ResponseEntity<?> getAcces(@RequestParam long matricula, @RequestParam String contrasena) throws JsonProcessingException {
        User user = userRepository.findUserByMatricula(matricula).get();
        String contraEncode = new String(Base64.getDecoder().decode(contrasena), StandardCharsets.UTF_8);
        String contraUser = new String(Base64.getDecoder().decode(user.getContrasena()), StandardCharsets.UTF_8);
        System.out.println(contraUser);
        if (!contraUser.equals(contraEncode)){
            throw new ResourceNotFoundException("Contraseña incorrecta");
        }
        String user1 = new ObjectMapper().writerWithView(JsonViewProfiles.User.class).writeValueAsString(user);
        return new ResponseEntity<>( user1,HttpStatus.OK);
    }

    @PutMapping("/cambiarContraseña")
    public ResponseEntity<?> updateUser(@RequestParam String contrasena, @RequestParam long matricula){
        User user1 = verifyUser(matricula);
        user1.setContrasena(contrasena);
        userRepository.save(user1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/cambiarFoto")
    public ResponseEntity<?> updateFoto(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
