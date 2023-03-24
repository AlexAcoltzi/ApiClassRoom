package com.buap.alex.backendbuapclassroom.Domain;

import com.buap.alex.backendbuapclassroom.Data.JsonViewProfiles;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "alumno_tarea")
@ToString
@JsonView(JsonViewProfiles.AlumnoTarea.class)
public class AlumnoTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumnoTarea")
    @Getter
    private long idAlumnoTarea;



    //Relación uno a uno con usuario, un usuario puede tener una tarea realizada
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    @Getter @Setter
    private User user;




    //Relación uno a uno, una tarea puede tener una calificación
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarea", referencedColumnName = "idtarea")
    @Getter @Setter
    private Tarea tarea;




    @Column(name = "ruta")
    @Getter @Setter
    private String ruta;




    @Column(name = "status")
    @Getter @Setter
    private String status;




    @Column(name = "calificacion")
    @Getter @Setter
    private int calificacion;
}
