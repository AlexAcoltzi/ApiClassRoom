package com.buap.alex.backendbuapclassroom.Controller;

import com.buap.alex.backendbuapclassroom.Domain.User;
import com.buap.alex.backendbuapclassroom.exception.ResourceNotFoundException;
import com.buap.alex.backendbuapclassroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {

    @Autowired
    UserRepository userRepository;

    protected User verifyUser(long matricula){
        Optional<User> user = userRepository.findUserByMatricula(matricula);
        if (!user.isPresent()){
            throw new ResourceNotFoundException("No se encontro el alumno con matricula " + matricula + " intenta de nuevo");
        }
        return user.get();
    }

    @PostMapping("/NewUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/getUserByMat/{matricula}")
    public ResponseEntity<?> getUserByMat(@RequestParam long matricula){
        return new ResponseEntity<>(verifyUser(matricula), HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAcces")
    public ResponseEntity<?> getAcces(@RequestParam long matricula, @RequestParam String contrasena){
        Optional<User> user = userRepository.findUserByMatricula(matricula);
        User userData = user.get();
        String contraDb = userData.getContrasena();
        if (contraDb.equals(contrasena)){
            return new ResponseEntity<>( user,HttpStatus.OK);
        }
        return new ResponseEntity<>("contraseña incorrecta",HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/cambiarContraseña/")
    public ResponseEntity<?> updateUser(@RequestParam String contrasena, @RequestParam long matricula){
        User user1 = verifyUser(matricula);
        user1.setContrasena(contrasena);
        userRepository.save(user1);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
