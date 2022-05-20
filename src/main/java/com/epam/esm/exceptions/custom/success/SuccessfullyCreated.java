package com.epam.esm.exceptions.custom.success;

public class SuccessfullyCreated extends RuntimeException{
    public SuccessfullyCreated(String message){
        super(message);
    }
}
