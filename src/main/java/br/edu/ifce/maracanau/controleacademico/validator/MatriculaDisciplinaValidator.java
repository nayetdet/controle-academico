package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.exception.MatriculaAlunoAndDisciplinaConflictException;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaRepository;
import org.springframework.stereotype.Component;

@Component
public class MatriculaDisciplinaValidator {

    private final MatriculaRepository matriculaRepository;

    public MatriculaDisciplinaValidator(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public void validateAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        if (matriculaRepository.existsByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)) {
            throw new MatriculaAlunoAndDisciplinaConflictException();
        }
    }

    public void validateAlunoMatriculaAndDisciplinaCodigoOnUpdate(Long id, String matriculaAluno, String codigoDisciplina) {
        if (matriculaRepository.existsByAlunoMatriculaAndDisciplinaCodigoAndIdNot(matriculaAluno, codigoDisciplina, id)) {
            throw new MatriculaAlunoAndDisciplinaConflictException();
        }
    }

}
