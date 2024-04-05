package com.cllaque.cuentams.Excepcion;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
