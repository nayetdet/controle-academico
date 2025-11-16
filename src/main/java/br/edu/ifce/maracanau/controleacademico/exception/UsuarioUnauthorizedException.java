package br.edu.ifce.maracanau.controleacademico.exception;

public class UsuarioUnauthorizedException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "O usuário não está autenticado.";

    public UsuarioUnauthorizedException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
