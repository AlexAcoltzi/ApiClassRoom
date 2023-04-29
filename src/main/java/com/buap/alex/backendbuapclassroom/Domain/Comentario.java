package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "Comentario")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonView(JsonViewProfiles.Comentario.class)
//Entidad que permite almacenar los comentarios realizados por los usuarios a un curso
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({JsonViewProfiles.Curso.class, JsonViewProfiles.Comentario.class})
    @Column(name = "idComent")
    @Getter
    private Long idComent;


    @Column(name = "comentario")
    @Getter
    @Setter
    private String comentario;


    //Relación muchos a uno, muchos comentarios pueden ser realizados por un usuario
    @ManyToOne
    @JoinColumn(name = "idUser")
    @Getter
    @Setter
    private User user;


    @Column(name = "fecha")
    @Getter
    @Setter
    private Date fecha;


    //Relación muchos a muchos, muchos comentarios pueden estar en muchos cursos.
    @ManyToMany
    @JoinTable(name = "comentarioCursos", joinColumns = @JoinColumn(name = "idComent"), inverseJoinColumns = @JoinColumn(name = "idCurso"))
    @Getter
    @Setter
    private List<Curso> cursos = new ArrayList<>();

    public void addCurso(Curso cursoToAdd) {
        this.cursos.add(cursoToAdd);
        cursoToAdd.getComentarios().add(this);
    }

    public void removeCurso(Curso cursoToRemove) {
        this.cursos.remove(cursoToRemove);
        cursoToRemove.getComentarios().remove(this);
    }

    public void addUser(User userToAdd) {
        this.setUser(userToAdd);
        userToAdd.getComentarios().add(this);
    }

    public void deleteUser(User userToDelete){
        this.setUser(null);
        userToDelete.getComentarios().remove(this);
    }
}
