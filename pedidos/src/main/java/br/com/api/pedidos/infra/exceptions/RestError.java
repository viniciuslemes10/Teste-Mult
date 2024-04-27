package br.com.api.pedidos.infra.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestError {
    private HttpStatus httpStatus;
    private String message;
}
