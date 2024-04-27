package br.com.api.pedidos.exception;

public class ViolationOfArgumentsException extends RuntimeException {
    public ViolationOfArgumentsException(String message) {
        super(message);
    }

    public ViolationOfArgumentsException() {
        super("Argumento inválido!");
    }
}
