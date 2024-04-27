package br.com.api.pedidos.infra.exceptions;

import br.com.api.pedidos.exception.ViolationOfArgumentsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ViolationOfArgumentsException.class)
    public ResponseEntity<RestError> enderecoUnique(ViolationOfArgumentsException exception) {
        RestError restErrorMessage = new RestError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Erro de formato JSON: certifique-se de que o JSON esteja no formato correto.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
