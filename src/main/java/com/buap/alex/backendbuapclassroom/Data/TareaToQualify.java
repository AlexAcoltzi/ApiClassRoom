package com.buap.alex.backendbuapclassroom.Data;

import lombok.Getter;
import lombok.Setter;

public class TareaToQualify {
    
    @Getter @Setter
    long idTarea;
    
    @Getter @Setter
    int calificacion;

    public TareaToQualify(long idTarea, int calificacion) {
        this.idTarea = idTarea;
        this.calificacion = calificacion;
    }
}
