package com.epam.esm.exceptions.custom;

public class NameCannotBeBlankException extends RuntimeException {
    public NameCannotBeBlankException(String s) {
        super(s);
    }
}
