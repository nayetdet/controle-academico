package br.edu.ifce.maracanau.controleacademico.exception;

public class DisciplinaCodigoConflictException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Uma disciplina com o mesmo código já existe.";

    public DisciplinaCodigoConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
