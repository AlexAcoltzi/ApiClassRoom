package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Curso")
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    @Getter @Setter
    private long idCurso;

    @Column(name = "NRC", nullable = false)
    @Getter @Setter
    private long NRC;

    @Column(name = "nombreCurso", nullable = false)
    @Getter @Setter
    private String nombreCurso;

    @Column(name = "diasCurso", nullable = false)
    @Getter @Setter
    private String diasCurso;

    @Column(name = "horarioCurso", nullable = false)
    @Getter @Setter
    private String horarioCurso;

    @Column(name = "ruta", nullable = false)
    @Getter @Setter
    private String ruta;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "cursos")
    @Getter @Setter
    private List<User> Alumnos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMaestro")
    private User maestro;


}
