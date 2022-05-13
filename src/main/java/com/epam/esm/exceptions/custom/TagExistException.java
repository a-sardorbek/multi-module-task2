package com.epam.esm.exceptions.custom;

public class TagExistException extends RuntimeException {
    public TagExistException(String message) {
        super(message);
    }
}
