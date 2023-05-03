package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "archivo")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonView(JsonViewProfiles.Archivo.class)
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({JsonViewProfiles.Curso.class, JsonViewProfiles.Archivo.class})
    @Column(name = "idArchivo")
    @Getter
    private long idArchivo;

    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;


    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;



    //Relación muchos a muchos, muchos archivos pueden estar en muchos cursos
    @ManyToMany(mappedBy = "archivos")
    @Getter @Setter
    private List<Curso> cursos;



    //Relación muchos a uno, muchos archivos pueden ser de un usuario
    @ManyToOne
    @JoinColumn(name = "autor")
    @Getter @Setter
    private User autor;

    public void addCursos(Curso cursoToAdd){
        this.cursos.add(cursoToAdd);
        cursoToAdd.getArchivos().add(this);
    }

    public void removeCursos(Curso cursoToRemove){
        this.cursos.remove(cursoToRemove);
        cursoToRemove.getArchivos().remove(this);
    }

    public void addAutor(User autorToAdd){
        this.setAutor(autorToAdd);
        autorToAdd.getArchivos().add(this);
    }

    public void removeAutor(User autorToRemove){
        this.setAutor(null);
        autorToRemove.getArchivos().remove(this);
    }
}
