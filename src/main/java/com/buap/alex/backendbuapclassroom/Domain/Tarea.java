package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Tarea")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@JsonView(JsonViewProfiles.Tarea.class)
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
    
    
    
    //Relación uno a muchos, una tarea puede tener muchos archivos de usuariosd cargadas
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<AlumnoTarea> alumnoTareas;



    //Relación muchos a uno, puede haber muchas tareas en un curso.
    @ManyToOne
    @JoinColumn(name = "curso_id")
    @Getter @Setter
    private Curso curso;

    public void addCurso(Curso cursoToAdd){
        this.setCurso(cursoToAdd);
        cursoToAdd.getTareas().add(this);
    }

    public void removeCurso(Curso cursoToRemove){
        this.setCurso(null);
        cursoToRemove.getTareas().remove(this);
    }
}
