package com.cllaque.movimientoms.exception;

public class GenericException extends RuntimeException{
    public GenericException(String message){
        super(message);
    }

    public GenericException(String message, Throwable t){
        super(message, t);
    }
}
