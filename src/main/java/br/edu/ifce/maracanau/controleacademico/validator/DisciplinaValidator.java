package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DisciplinaValidator {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaValidator(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public void validarCodigoUnico(String codigo, Long idAtual) {
        Optional<Disciplina> existente = disciplinaRepository.findByCodigoIgnoreCase(codigo);
        if (existente.isPresent() && (idAtual == null || !existente.get().getId().equals(idAtual))) {
            throw new IllegalStateException("Já existe uma disciplina com este código.");
        }
    }
}
