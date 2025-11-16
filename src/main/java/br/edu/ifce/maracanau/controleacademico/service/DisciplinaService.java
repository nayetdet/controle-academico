package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.DisciplinaCodeConflictException;
import br.edu.ifce.maracanau.controleacademico.exception.DisciplinaNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.DisciplinaMapper;
import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.payload.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.DisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.DisciplinaValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaMapper disciplinaMapper;
    private final DisciplinaValidator disciplinaValidator;

    public DisciplinaService(
            DisciplinaRepository disciplinaRepository,
            DisciplinaMapper disciplinaMapper,
            DisciplinaValidator disciplinaValidator
    ) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaValidator = disciplinaValidator;
    }

    @Transactional(readOnly = true)
    public ApplicationPage<DisciplinaDTO> search(DisciplinaQuery query) {
        Page<Disciplina> page = disciplinaRepository.search(
                query.getNome(),
                query.getCodigo(),
                query.getCargaHorariaMinima(),
                query.getCargaHorariaMaxima(),
                query.getSemestre(),
                query.getPageable()
        );

        return new ApplicationPage<>(page.map(disciplinaMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<DisciplinaDTO> findByCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo).map(disciplinaMapper::toDTO);
    }

    @Transactional
    public DisciplinaDTO create(DisciplinaRequest request) {
        disciplinaValidator.validateCodigo(request.codigo());
        Disciplina disciplina = disciplinaRepository.save(disciplinaMapper.toModel(request));
        return disciplinaMapper.toDTO(disciplina);
    }

    @Transactional
    public DisciplinaDTO update(String codigo, DisciplinaUpdateRequest request) {
        Disciplina disciplina = disciplinaRepository.findByCodigo(codigo).orElseThrow(DisciplinaNotFoundException::new);
        disciplinaMapper.update(disciplina, request);
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDTO(disciplina);
    }

    @Transactional
    public void deleteByCodigo(String codigo) {
        if (!disciplinaRepository.existsByCodigo(codigo)) {
            throw new DisciplinaNotFoundException();
        }

        disciplinaRepository.deleteByCodigo(codigo);
    }

}
