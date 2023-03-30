package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import lombok.Getter;
import lombok.Setter;

public class ModificarCurso {

    @Getter @Setter
    private Curso curso;

    @Getter @Setter
    private long idCurso;


    public ModificarCurso(Curso curso, long idCurso) {
        this.curso = curso;
        this.idCurso = idCurso;
    }
}
