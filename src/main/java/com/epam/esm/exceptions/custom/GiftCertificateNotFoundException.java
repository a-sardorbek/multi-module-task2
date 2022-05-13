package com.epam.esm.exceptions.custom;

public class GiftCertificateNotFoundException extends RuntimeException{
    public GiftCertificateNotFoundException(String mesage){
        super(mesage);
    }
}
