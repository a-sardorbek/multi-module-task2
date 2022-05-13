package com.epam.esm.exceptions.custom;

public class InputNotMatchException extends RuntimeException {
    public InputNotMatchException(String message) {
        super(message);
    }
}
