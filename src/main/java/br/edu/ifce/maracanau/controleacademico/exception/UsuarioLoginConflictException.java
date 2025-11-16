package br.edu.ifce.maracanau.controleacademico.exception;

public class UsuarioLoginConflictException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Um usuário com o mesmo login já existe.";

    public UsuarioLoginConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
