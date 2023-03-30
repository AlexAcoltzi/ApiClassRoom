package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import lombok.Getter;
import lombok.Setter;

public class ModificarTarea {

    @Getter @Setter
    private Tarea tarea;

    @Getter @Setter
    private long idTarea;


    public ModificarTarea(Tarea tarea, long idTarea) {
        this.tarea = tarea;
        this.idTarea = idTarea;
    }
}
