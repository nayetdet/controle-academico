package br.edu.ifce.maracanau.controleacademico.exception;

public class UsuarioModificationForbiddenException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Você não tem permissão de modificar este usuário.";

    public UsuarioModificationForbiddenException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
