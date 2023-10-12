package com.go2geda.Go2GedaApp.exceptions;

import java.io.Serial;

public class Go2gedaBaseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1 ;

    public Go2gedaBaseException(String message){
        super(message);
    }
}
