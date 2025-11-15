package br.edu.ifce.maracanau.controleacademico.exception;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

}
