package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.exception.DisciplinaCodigoConflictException;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaValidator {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaValidator(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public void validateCodigo(String codigo) {
        if (codigo != null && disciplinaRepository.existsByCodigo(codigo)) {
            throw new DisciplinaCodigoConflictException();
        }
    }

}
