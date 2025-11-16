package br.edu.ifce.maracanau.controleacademico.exception;

public class AlunoMatriculaConflictException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Um aluno com a mesma matrícula já existe.";

    public AlunoMatriculaConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
