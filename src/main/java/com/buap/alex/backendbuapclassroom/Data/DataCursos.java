package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Archivo;
import com.buap.alex.backendbuapclassroom.Domain.Comentario;
import com.buap.alex.backendbuapclassroom.Domain.Tarea;
import com.buap.alex.backendbuapclassroom.Domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DataCursos {
    @Getter @Setter
    private List<User> usuarios;

    @Getter @Setter
    private List<Comentario> comentarios;

    @Getter @Setter
    private List<Archivo> archivos;

    @Getter @Setter
    private List<Tarea> tareas;

    public DataCursos(List<User> usuarios, List<Comentario> comentarios, List<Archivo> archivos, List<Tarea> tareas) {
        this.usuarios = usuarios;
        this.comentarios = comentarios;
        this.archivos = archivos;
        this.tareas = tareas;
    }
}
