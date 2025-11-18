package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.exception.MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaDisciplinaRepository;
import org.springframework.stereotype.Component;

@Component
public class MatriculaDisciplinaValidator {

    private final MatriculaDisciplinaRepository matriculaDisciplinaRepository;

    public MatriculaDisciplinaValidator(MatriculaDisciplinaRepository matriculaDisciplinaRepository) {
        this.matriculaDisciplinaRepository = matriculaDisciplinaRepository;
    }

    public void validateAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        if (matriculaDisciplinaRepository.existsByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)) {
            throw new MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException();
        }
    }

    public void validateAlunoMatriculaAndDisciplinaCodigoOnUpdate(Long id, String matriculaAluno, String codigoDisciplina) {
        if (matriculaDisciplinaRepository.existsByAlunoMatriculaAndDisciplinaCodigoAndIdNot(matriculaAluno, codigoDisciplina, id)) {
            throw new MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException();
        }
    }

}
