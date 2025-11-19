package br.edu.ifce.maracanau.controleacademico.exception;

public class MatriculaAlunoAndDisciplinaConflictException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "Não é possível registrar a matrícula: o aluno já possui vínculo ativo com esta disciplina.";

    public MatriculaAlunoAndDisciplinaConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
