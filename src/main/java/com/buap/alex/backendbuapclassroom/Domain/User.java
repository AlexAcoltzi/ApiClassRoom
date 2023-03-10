package com.buap.alex.backendbuapclassroom.Domain;


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
public class User {
    @Id
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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "AlumnoCursos", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idCurso")
    )
    @Getter @Setter
    private List<Curso> cursos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private List<Curso> cursosMaestros;


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
