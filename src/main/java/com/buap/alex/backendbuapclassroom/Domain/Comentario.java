package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Comentario")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComent")
    @Getter @Setter
    private Long id;

    @Column(name = "comentario")
    @Getter @Setter
    private String comentario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser")
    @Getter @Setter
    private User user;

    @Column(name = "fecha")
    @Getter @Setter
    private Date fecha;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "comentarioCursos", joinColumns = @JoinColumn(name = "idComent"), inverseJoinColumns = @JoinColumn(name = "idCurso"))
    @Getter @Setter
    private List<Curso> cursos;


}
