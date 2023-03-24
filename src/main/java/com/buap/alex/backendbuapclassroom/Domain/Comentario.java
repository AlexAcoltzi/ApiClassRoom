package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    @Getter @Setter
    private String comentario;



    //Relación muchos a uno, muchos comentarios pueden ser realizados por un usuario
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser")
    @Getter @Setter
    private User user;



    @Column(name = "fecha")
    @Getter @Setter
    private Date fecha;



    //Relación muchos a muchos, muchos comentarios pueden estar en muchos cursos.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "comentarioCursos", joinColumns = @JoinColumn(name = "idComent"), inverseJoinColumns = @JoinColumn(name = "idCurso"))
    @Getter @Setter
    private List<Curso> cursos;
}
