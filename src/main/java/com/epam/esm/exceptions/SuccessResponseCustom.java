package com.epam.esm.exceptions;

public class SuccessResponseCustom {
    private Integer httpStatusCode;
    private String message;

    SuccessResponseCustom(){}

    public SuccessResponseCustom(Integer httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
