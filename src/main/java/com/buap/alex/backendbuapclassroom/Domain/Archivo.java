package com.buap.alex.backendbuapclassroom.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "archivo")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArchivo")
    @Getter
    private long ididArchivo;

    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;


    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;



    //Relación muchos a muchos, muchos archivos pueden estar en muchos cursos
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "archivos")
    @Getter @Setter
    private List<Curso> cursos;



    //Relación muchos a uno, muchos archivos pueden ser de un usuario
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor")
    @Getter
    private User autor;
}
