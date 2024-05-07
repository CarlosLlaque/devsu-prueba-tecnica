package com.cllaque.personams.exception;

public class GenericException extends RuntimeException{
    public GenericException(String message){
        super(message);
    }

    public GenericException(String message, Throwable t){
        super(message, t);
    }
}
