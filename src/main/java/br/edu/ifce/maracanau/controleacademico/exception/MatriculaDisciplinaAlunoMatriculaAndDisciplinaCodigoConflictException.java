package br.edu.ifce.maracanau.controleacademico.exception;

public class MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "Não é possível registrar a matrícula: o aluno já possui vínculo ativo com esta disciplina.";

    public MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
