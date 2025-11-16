package br.edu.ifce.maracanau.controleacademico.exception;

public class MatriculaDisciplinaNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Matrícula não encontrada.";

    public MatriculaDisciplinaNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
