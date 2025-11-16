package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.exception.AlunoMatriculaConflictException;
import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoValidator {

    private final AlunoRepository alunoRepository;

    public AlunoValidator(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void validateMatricula(String matricula) {
        if (matricula != null && alunoRepository.existsByMatricula(matricula)) {
            throw new AlunoMatriculaConflictException();
        }
    }

}
