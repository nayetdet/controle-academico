package br.edu.ifce.maracanau.controleacademico.exception;

public class DisciplinaCodeConflictException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Uma disciplina com o mesmo código já existe.";

    public DisciplinaCodeConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
