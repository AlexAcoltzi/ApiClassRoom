package com.buap.alex.backendbuapclassroom.Domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "Usuario")
@ToString
@EqualsAndHashCode
@NoArgsConstructor

//Entidad con los datos necesarios de un usuario en el sistema
public class User {
    @Id //Id único con el que se reconoce al usuario
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    @Getter
    private long idUser;


    @Column(name = "correo", nullable = false, unique = true)
    @Getter @Setter
    private String correo;


    @Column(name = "contrasena", nullable = false)
    @Getter @Setter
    private String contrasena;


    @Column(name = "matricula", nullable = false, unique = true)
    @Getter @Setter
    private long matricula;


    @Column(name = "nombre", nullable = false)
    @Getter @Setter
    private String nombre;


    @Column(name = "tipo", nullable = false)
    @Getter @Setter
    private int tipo;


    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;




    /*Relación de muchos a muchos con cursos, en cuestión de alumnos (Un alumno puede estar inscrito en muchos cursos y
    muchos cursos pueden tener inscritos a muchos alumnos)
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "AlumnoCursos", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idCurso")
    )
    @Getter @Setter
    private List<Curso> cursos;



    /*Relación uno a muchos con cursos en relación con maestros(un maestro puede tener asignado y
    muchos cursos pueden tener asignado a un maestro)
     */
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    @Getter @Setter
    private List<Curso> cursosMaestros;


    /*Relación uno a muchos, un usuario puede tener muchos comentarios*/
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter @Setter
    private List<Comentario> comentarios;



    //Relación uno a muchos, un usuario puede tener muchos archivos
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
    @Getter @Setter
    private List<Archivo> archivos;




    public User(long idUser, String correo, String contrasena, long matricula, String nombre, int tipo, String ruta) {
        this.idUser = idUser;
        this.correo = correo;
        this.contrasena = contrasena;
        this.matricula = matricula;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ruta = ruta;
    }
}
