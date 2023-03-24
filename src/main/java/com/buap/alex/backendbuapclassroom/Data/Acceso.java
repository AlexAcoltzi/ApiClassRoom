package com.buap.alex.backendbuapclassroom.Data;

import lombok.Getter;
import lombok.Setter;

public class Acceso {

    @Getter
    private long matricula;

    @Getter
    private String contrasena;

    public Acceso(long matricula, String contrasena) {
        this.matricula = matricula;
        this.contrasena = contrasena;
    }
}
