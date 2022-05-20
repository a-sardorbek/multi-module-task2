package com.epam.esm.exceptions.custom;

public class PriceIncorrectException extends RuntimeException{
    public PriceIncorrectException(String message){
        super(message);
    }
}
