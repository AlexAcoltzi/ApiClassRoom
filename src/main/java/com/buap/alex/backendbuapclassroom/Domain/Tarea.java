package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Tarea")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
//Entidad que nos ayudara a crear actividades asignadas a los cursos.
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarea")
    @Getter
    private long idTarea;



    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;



    @Column(name = "descripcion")
    @Getter @Setter
    private String descripcion;



    @Column(name = "fechaDePublicacion")
    @Getter @Setter
    private String fechaDePublicacion;



    @Column(name = "horaDePublicacion")
    @Getter @Setter
    private Date horaDePublicacion;



    @Column(name = "fechaLimite")
    @Getter @Setter
    private Date fechaLimite;



    @Column(name = "horaLimite")
    @Getter @Setter
    private String horaLimite;



    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;



    //Relación muchos a uno, puede haber muchas tareas en un curso.
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter @Setter
    private Curso curso;
}
