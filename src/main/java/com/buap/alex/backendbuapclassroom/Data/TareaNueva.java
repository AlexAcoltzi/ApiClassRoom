package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import lombok.Getter;
import lombok.Setter;

public class TareaNueva {


    @Getter @Setter
    private Tarea tarea;

    @Getter @Setter
    private long idCurso;


    public TareaNueva(Tarea tarea, long idCurso) {
        this.tarea = tarea;
        this.idCurso = idCurso;
    }
}
