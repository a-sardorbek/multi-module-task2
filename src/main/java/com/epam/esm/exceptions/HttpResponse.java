package com.epam.esm.exceptions;


public class HttpResponse {

    private Integer httpStatusCode;
    private ResponseMessage responseMessage;

    public HttpResponse(){}

    public HttpResponse(Integer httpStatusCode, ResponseMessage responseMessage) {
        this.httpStatusCode = httpStatusCode;
        this.responseMessage = responseMessage;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }
}
