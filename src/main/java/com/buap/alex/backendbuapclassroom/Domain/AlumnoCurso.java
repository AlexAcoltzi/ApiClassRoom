package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table (name = "AlumnoCurso")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AlumnoCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumnoCurso")
    @Getter
    private long id;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Curso.class)
    @JoinColumn(name = "idCursoRef", referencedColumnName = "idCurso")
    @Getter
    private Curso cursos;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "idUserRef", referencedColumnName = "idUser")
    private List<User> users;
}
