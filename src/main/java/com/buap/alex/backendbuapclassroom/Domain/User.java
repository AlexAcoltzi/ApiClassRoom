package com.buap.alex.backendbuapclassroom.Domain;


import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Usuario")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonView(JsonViewProfiles.User.class)
//Entidad con los datos necesarios de un usuario en el sistema
public class User {
    @Id //Id único con el que se reconoce al usuario
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({JsonViewProfiles.User.class, JsonViewProfiles.Curso.class})
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
    @ManyToMany(fetch =FetchType.LAZY)
    @JoinTable(
            name = "AlumnoCursos", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idCurso")
    )
    @Getter @Setter
    private List<Curso> cursos = new ArrayList<>();



    /*Relación uno a muchos con cursos en relación con maestros(un maestro puede tener asignado y
    muchos cursos pueden tener asignado a un maestro)
     */
    @OneToMany(mappedBy = "maestro")
    @Getter @Setter
    private List<Curso> cursosMaestros = new ArrayList<>();


    /*Relación uno a muchos, un usuario puede tener muchos comentarios*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Comentario> comentarios = new ArrayList<>();



    //Relación uno a muchos, un usuario puede tener muchos archivos
    @OneToMany(mappedBy = "autor", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Archivo> archivos = new ArrayList<>();



    //Relación uno a uno con tareas cargadas
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<AlumnoTarea> alumnoTarea = new ArrayList<>();




    public User(long idUser, String correo, String contrasena, long matricula, String nombre, int tipo, String ruta) {
        this.idUser = idUser;
        this.correo = correo;
        this.contrasena = contrasena;
        this.matricula = matricula;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ruta = ruta;
    }

    public void addCursos(Curso cursoToAdd){
        this.cursos.add(cursoToAdd);
        cursoToAdd.getAlumnos().add(this);
    }

    public void removeCursos(Curso cursoTopRemove){
        this.cursos.remove(cursoTopRemove);
        cursoTopRemove.getAlumnos().remove(this);
    }

    public void addCursoMa(Curso cursoToAdd){
        this.cursosMaestros.add(cursoToAdd);
        cursoToAdd.setMaestro(this);
    }

    public void removeCursoMa(Curso cursoToRemove){
        this.cursosMaestros.remove(cursoToRemove);
        cursoToRemove.setMaestro(null);
    }

   /* public void addComment(Comentario comentarioToAdd){
        this.comentarios.add(comentarioToAdd);
        comentarioToAdd.setUser(this);
    }

    public void addArchivos(Archivo archivoToAdd){
        this.archivos.add(archivoToAdd);
        archivoToAdd.setAutor(this);
    }

    public void addTareaAlumno(AlumnoTarea tareaToAddAlumn){
        this.alumnoTarea.add(tareaToAddAlumn);
        tareaToAddAlumn.setUser(this);
    }*/

}
