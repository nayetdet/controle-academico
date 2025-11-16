package br.edu.ifce.maracanau.controleacademico.exception;

public class UsuarioNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Usuário não encontrado.";

    public UsuarioNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
