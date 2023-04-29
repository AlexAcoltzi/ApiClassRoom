package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Comentario;
import lombok.Getter;
import lombok.Setter;

public class DataComentario {

    @Getter @Setter
    private Comentario comentario;

    @Getter @Setter
    private long idUser;

    @Getter @Setter
    private long idCurso;


    public DataComentario(Comentario comentario, long idUser, long idCurso) {
        this.comentario = comentario;
        this.idUser = idUser;
        this.idCurso = idCurso;
    }
}
