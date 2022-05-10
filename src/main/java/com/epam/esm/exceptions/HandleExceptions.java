package com.epam.esm.exceptions;

import com.epam.esm.exceptions.custom.*;
import com.epam.esm.utils.ErrorCodeUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class HandleExceptions {


    @Autowired
    private Gson gson;

    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";



    private ResponseEntity<String> createHttpResponse(HttpStatus httpStatus, String message,Integer errorCode) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorMessage(message);
        responseMessage.setErrorCode(errorCode);

        HttpResponse response = new HttpResponse();
        response.setHttpStatusCode(httpStatus.value());
        response.setResponseMessage(responseMessage);

        return new ResponseEntity<>(gson.toJson(response),httpStatus);
    }

    private ResponseEntity<String> createHttpResponse(HttpStatus httpStatus, String message) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorMessage(message);
        responseMessage.setErrorCode(401003);

        HttpResponse response = new HttpResponse();
        response.setHttpStatusCode(httpStatus.value());
        response.setResponseMessage(responseMessage);

        return new ResponseEntity<>(gson.toJson(response),httpStatus);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> internalServerErrorException(Exception exception) {
//        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG,ErrorCodeUtils.MAPPING_NOT_FOUND);
//    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<String> giftCertificateNotFound(){
        return createHttpResponse(HttpStatus.NOT_FOUND, "Gift certificate not found",ErrorCodeUtils.NOT_FOUND_CODE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods().iterator().next());
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> tagNotFoundException(){
        return createHttpResponse(HttpStatus.NOT_FOUND,"Tag not found",ErrorCodeUtils.NOT_FOUND_CODE);
    }

}
