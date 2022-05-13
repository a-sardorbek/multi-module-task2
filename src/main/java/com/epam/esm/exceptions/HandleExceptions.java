package com.epam.esm.exceptions;

import com.epam.esm.exceptions.custom.*;
import com.epam.esm.utils.ErrorCodeUtils;
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

    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message,Integer errorCode) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorMessage(message);
        responseMessage.setErrorCode(errorCode);

        HttpResponse response = new HttpResponse();
        response.setHttpStatusCode(httpStatus.value());
        response.setResponseMessage(responseMessage);

        return new ResponseEntity<>(response,httpStatus);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorMessage(message);
        responseMessage.setErrorCode(401003);

        HttpResponse response = new HttpResponse();
        response.setHttpStatusCode(httpStatus.value());
        response.setResponseMessage(responseMessage);

        return new ResponseEntity<>(response,httpStatus);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> internalServerErrorException(Exception exception) {
//        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG,ErrorCodeUtils.MAPPING_NOT_FOUND);
//    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<HttpResponse> giftCertificateNotFound(GiftCertificateNotFoundException giftCertificateNotFoundException){
        return createHttpResponse(HttpStatus.NOT_FOUND, giftCertificateNotFoundException.getMessage(),ErrorCodeUtils.NOT_FOUND_CODE);
    }

    @ExceptionHandler(GiftCertificateExistException.class)
    public ResponseEntity<HttpResponse> giftCertificateExist(GiftCertificateExistException giftCertificateExistException){
        return createHttpResponse(ALREADY_REPORTED, giftCertificateExistException.getMessage(),ErrorCodeUtils.EXIST);
    }

    @ExceptionHandler(TagExistException.class)
    public ResponseEntity<HttpResponse> tagExist(TagExistException tagExistException){
        return createHttpResponse(ALREADY_REPORTED, tagExistException.getMessage(),ErrorCodeUtils.EXIST);
    }

    @ExceptionHandler(GiftAndTagWithGivenIdsConnectedException.class)
    public ResponseEntity<HttpResponse> giftAndTagConnected(GiftAndTagWithGivenIdsConnectedException giftAndTagWithGivenIdsConnectedException){
        return createHttpResponse(ALREADY_REPORTED, giftAndTagWithGivenIdsConnectedException.getMessage(),ErrorCodeUtils.EXIST);
    }

    @ExceptionHandler(InputNotMatchException.class)
    public ResponseEntity<HttpResponse> giftAndTagConnected(InputNotMatchException inputNotMatchException){
        return createHttpResponse(BAD_REQUEST, inputNotMatchException.getMessage(),ErrorCodeUtils.INPUT_NOT_MATCH);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods().iterator().next());
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<HttpResponse> tagNotFoundException(TagNotFoundException tagNotFoundException){
        return createHttpResponse(HttpStatus.NOT_FOUND,tagNotFoundException.getMessage(),ErrorCodeUtils.NOT_FOUND_CODE);
    }

}
