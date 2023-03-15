package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "alumno_tarea")
@ToString
public class AlumnoTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    @Getter @Setter
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarea", referencedColumnName = "idtarea")
    @Getter @Setter
    private Tarea tarea;

    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;

    @Column(name = "calificacion")
    @Getter @Setter
    private int calificacion;
}
