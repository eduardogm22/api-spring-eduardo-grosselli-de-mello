package com.ideau.cadastroProdutos.exception;

import com.ideau.cadastroProdutos.model.RespostaGenericaHTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SimplesHttpException.class)
    public ResponseEntity<RespostaGenericaHTTP> httpExceptionHandler(SimplesHttpException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new RespostaGenericaHTTP(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RespostaGenericaHTTP> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespostaGenericaHTTP("Erro inesperado: " + e.getMessage()));
    }
}
