package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.repository.MatriculaRepository;
import org.springframework.stereotype.Component;

@Component
public class MatriculaValidator {

    private final MatriculaRepository matriculaRepository;

    public MatriculaValidator(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public void validarDuplicidade(Long alunoId, Long disciplinaId, Long idAtual) {
        boolean existeOutra = idAtual == null
                ? matriculaRepository.existsByAlunoIdAndDisciplinaId(alunoId, disciplinaId)
                : matriculaRepository.existsByAlunoIdAndDisciplinaIdAndIdNot(alunoId, disciplinaId, idAtual);
        if (existeOutra) {
            throw new IllegalStateException("O aluno já está matriculado nesta disciplina.");
        }
    }

}
