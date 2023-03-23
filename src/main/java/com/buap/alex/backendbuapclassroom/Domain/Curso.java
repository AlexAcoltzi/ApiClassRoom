package com.buap.alex.backendbuapclassroom.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Curso")
@EqualsAndHashCode
@NoArgsConstructor
@ToString
//Entidad curso la cual nos ayuda a saber que curso, quien lo imparte y quienes están inscritos al mismo
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    @Getter
    private long idCurso;



    @Column(name = "nrc", nullable = false, unique = true)
    @Getter @Setter
    private long nrc;



    @Column(name = "nombreCurso", nullable = false)
    @Getter @Setter
    private String nombre;



    @Column(name = "diasCurso", nullable = false)
    @Getter @Setter
    private String dias;



    @Column(name = "horarioCurso", nullable = false)
    @Getter @Setter
    private String horario;



    @Column(name = "ruta", nullable = false)
    @Getter @Setter
    private String ruta;


    /*Relación muchos a muchos, un curso puede tener muchos alumnos inscritos*/
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "cursos")
    @Getter @Setter
    private List<User> Alumnos;


    /*Relación muchos a uno, muchos cursos pueden ser impartidos por un profesor*/
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMaestro")
    @Getter @Setter
    private User maestro;


    /*Relación uno a muchos, un curso puede tener muchas tareas*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    @Getter @Setter
    private List<Tarea> tareas;


    /*Relación muchos a muchos, muchos cursos pueden tener muchos comentarios*/
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "cursos")
    @Getter @Setter
    private List<Comentario> comentarios;



    //Relación muchos a muchos, muchos cursos pueden tener muchos archivos
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ArchivosCurso", joinColumns = @JoinColumn(name = "idCurso"), inverseJoinColumns = @JoinColumn(name = "idArchivo")
    )
    @Getter @Setter
    private List<Archivo> archivos;
}
