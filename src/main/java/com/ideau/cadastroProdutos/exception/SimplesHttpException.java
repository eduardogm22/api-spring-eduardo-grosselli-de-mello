package com.ideau.cadastroProdutos.exception;

import org.springframework.http.HttpStatus;

public class SimplesHttpException extends RuntimeException {
    HttpStatus httpStatus;
    public SimplesHttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
