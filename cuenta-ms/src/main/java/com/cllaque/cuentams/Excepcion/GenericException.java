package com.cllaque.cuentams.Excepcion;

public class GenericException extends RuntimeException{
    public GenericException(String message){
        super(message);
    }

    public GenericException(String message, Throwable t){
        super(message, t);
    }
}
