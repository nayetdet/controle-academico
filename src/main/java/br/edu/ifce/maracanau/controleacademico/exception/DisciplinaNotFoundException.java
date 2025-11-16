package br.edu.ifce.maracanau.controleacademico.exception;

public class DisciplinaNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Disciplina não encontrada.";

    public DisciplinaNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
