package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Curso")
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@JsonView(JsonViewProfiles.Curso.class)
//Entidad curso la cual nos ayuda a saber que curso, quien lo imparte y quienes están inscritos al mismo
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({JsonViewProfiles.Curso.class, JsonViewProfiles.User.class, JsonViewProfiles.Comentario.class, JsonViewProfiles.Archivo.class, JsonViewProfiles.AlumnoTarea.class, JsonViewProfiles.Tarea.class})
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
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cursos")
    @Getter @Setter
    private List<User> alumnos = new ArrayList<>();


    /*Relación muchos a uno, muchos cursos pueden ser impartidos por un profesor*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMaestro")
    @Getter @Setter
    private User maestro;


    //Relación uno a muchos, un curso puede tener muchas tareas
    @OneToMany( mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Tarea> tareas = new ArrayList<>();


    //Relación muchos a muchos, muchos cursos pueden tener muchos comentarios
    @ManyToMany( fetch = FetchType.LAZY, mappedBy = "cursos", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Comentario> comentarios = new ArrayList<>();



    //Relación muchos a muchos, muchos cursos pueden tener muchos archivos
    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(
            name = "ArchivosCurso", joinColumns = @JoinColumn(name = "idCurso"), inverseJoinColumns = @JoinColumn(name = "idArchivo")
    )
    @Getter @Setter
    private Set<Archivo> archivos = new HashSet<Archivo>();

    public void addUser(User userToAdd){
        if (userToAdd.getTipo() == 1){
            this.alumnos.add(userToAdd);
            userToAdd.getCursos().add(this);
        } else {
            this.setMaestro(userToAdd);
            userToAdd.getCursosMaestros().add(this);
        }
    }

    public void removeUser(User userToRemove){
        if (userToRemove.getTipo() == 1){
            this.alumnos.remove(userToRemove);
            userToRemove.getCursos().remove(this);
        } else{
            this.setMaestro(null);
            userToRemove.getCursosMaestros().remove(this);
        }
    }

    /*public void addAlum(User userToAdd){
        this.Alumnos.add(userToAdd);
        userToAdd.getCursos().add(this);
    }

    public void removeAlumn(User userToRemove){
        this.Alumnos.remove(userToRemove);
        userToRemove.getCursos().remove(userToRemove);
    }

    public void addMaestro(User maestroToAdd){
        this.setMaestro(maestroToAdd);
        maestroToAdd.getCursosMaestros().add(this);
    }

    public void removeMaestro(User maestroToRemove){
        this.setMaestro(null);
        maestroToRemove.getCursosMaestros().remove(this);
    }*/

   /* public void addTareas(Tarea tareaToAdd){
        this.tareas.add(tareaToAdd);
        tareaToAdd.setCurso(this);
    }


    public void addComments(Comentario commentToAdd){
        this.comentarios.add(commentToAdd);
        commentToAdd.getCursos().add(this);
    }*/

    public void addFile(Archivo archivoToAdd){
        this.archivos.add(archivoToAdd);
        archivoToAdd.getCursos().add(this);
    }

    public void removeFile(Archivo archivoToRemove){
        this.archivos.remove(archivoToRemove);
        archivoToRemove.getCursos().remove(this);
    }
}
