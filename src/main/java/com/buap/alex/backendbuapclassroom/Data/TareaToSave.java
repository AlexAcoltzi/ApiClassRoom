package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.AlumnoTarea;
import lombok.Getter;
import lombok.Setter;

public class TareaToSave {

    @Getter @Setter
    AlumnoTarea alumnoTarea;

    @Getter @Setter
    long idUser;

    @Getter @Setter
    long idTarea;

    public TareaToSave(AlumnoTarea alumnoTarea, long idUser, long idTarea) {
        this.alumnoTarea = alumnoTarea;
        this.idUser = idUser;
        this.idTarea = idTarea;
    }
}
