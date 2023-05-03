package com.buap.alex.backendbuapclassroom.Data;

import com.buap.alex.backendbuapclassroom.Domain.Archivo;
import com.buap.alex.backendbuapclassroom.Domain.User;
import lombok.Getter;
import lombok.Setter;

public class FileToSave {

    @Getter
    String code64;

    @Getter
    String type;

    @Getter
    Archivo archivo;

    @Getter
    long idUser;

    @Getter
    long idClase;

}
