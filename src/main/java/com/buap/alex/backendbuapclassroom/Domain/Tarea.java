package com.buap.alex.backendbuapclassroom.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Tarea")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarea")
    @Getter @Setter
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
    private String horaDePublicacion;

    @Column(name = "fechaLimite")
    @Getter @Setter
    private String fechaLimite;

    @Column(name = "horaLimite")
    @Getter @Setter
    private String horaLimite;

    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;
}
