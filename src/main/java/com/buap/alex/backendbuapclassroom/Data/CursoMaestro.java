package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Curso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Clase con la cual vamos a obtener metadatos necesarios para crear y asignar un profesor a la clase
@NoArgsConstructor
public class CursoMaestro {
    @Getter @Setter
    private Curso curso;
    @Getter @Setter
    private long id;

    public CursoMaestro(Curso curso, long id) {
        this.curso = curso;
        this.id = id;
    }
}
