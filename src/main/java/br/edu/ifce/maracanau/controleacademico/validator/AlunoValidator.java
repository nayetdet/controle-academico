package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoValidator {

    private final AlunoRepository alunoRepository;

    public AlunoValidator(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void validarMatriculaUnica(String matricula, Long idAtual) {
        boolean jaExiste = idAtual == null
                ? alunoRepository.existsByMatriculaIgnoreCase(matricula)
                : alunoRepository.existsByMatriculaIgnoreCaseAndIdNot(matricula, idAtual);
        if (jaExiste) {
            throw new IllegalStateException("Já existe um aluno com esta matrícula.");
        }
    }

}
