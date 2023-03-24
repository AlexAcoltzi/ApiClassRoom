package com.buap.alex.backendbuapclassroom.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
