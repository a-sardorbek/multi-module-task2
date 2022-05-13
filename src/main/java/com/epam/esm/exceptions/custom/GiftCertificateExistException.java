package com.epam.esm.exceptions.custom;

public class GiftCertificateExistException extends RuntimeException {
    public GiftCertificateExistException(String message){
        super(message);
    }
}
