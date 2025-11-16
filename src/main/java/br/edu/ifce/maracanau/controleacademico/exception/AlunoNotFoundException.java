package br.edu.ifce.maracanau.controleacademico.exception;

public class AlunoNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Aluno não encontrado.";

    public AlunoNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
