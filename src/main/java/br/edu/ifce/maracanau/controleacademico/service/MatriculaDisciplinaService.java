package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.MatriculaDisciplinaNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.MatriculaDisciplinaMapper;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.MatriculaDisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaDisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaDisciplinaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.MatriculaDisciplinaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MatriculaDisciplinaService {

    private final MatriculaDisciplinaRepository matriculaDisciplinaRepository;
    private final MatriculaDisciplinaMapper matriculaDisciplinaMapper;
    private final MatriculaDisciplinaValidator matriculaDisciplinaValidator;

    public MatriculaDisciplinaService(
            MatriculaDisciplinaRepository matriculaDisciplinaRepository,
            MatriculaDisciplinaMapper matriculaDisciplinaMapper,
            MatriculaDisciplinaValidator matriculaDisciplinaValidator
    ) {
        this.matriculaDisciplinaRepository = matriculaDisciplinaRepository;
        this.matriculaDisciplinaMapper = matriculaDisciplinaMapper;
        this.matriculaDisciplinaValidator = matriculaDisciplinaValidator;
    }

    @Transactional(readOnly = true)
    public ApplicationPage<MatriculaDisciplinaDTO> search(MatriculaDisciplinaQuery query) {
        return null;
    }

    @Transactional(readOnly = true)
    public Optional<MatriculaDisciplinaDTO> findById(Long id) {
        return matriculaDisciplinaRepository.findById(id).map(matriculaDisciplinaMapper::toDTO);
    }

    @Transactional
    public MatriculaDisciplinaDTO create(MatriculaDisciplinaRequest request) {
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        if (!matriculaDisciplinaRepository.existsById(id)) {
            throw new MatriculaDisciplinaNotFoundException();
        }

        matriculaDisciplinaRepository.deleteById(id);
    }

}
