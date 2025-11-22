package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.mapper.DisciplinaMapper;
import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.DisciplinaValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioService usuarioService;
    private final DisciplinaValidator disciplinaValidator;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, UsuarioService usuarioService, DisciplinaValidator disciplinaValidator) {
        this.disciplinaRepository = disciplinaRepository;
        this.usuarioService = usuarioService;
        this.disciplinaValidator = disciplinaValidator;
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findAll() {
        return disciplinaRepository.findAll(Sort.by("nome").ascending()).stream()
                .map(DisciplinaMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DisciplinaDTO findById(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Disciplina não encontrada"));
        return DisciplinaMapper.toDto(disciplina);
    }

    @Transactional
    public DisciplinaDTO create(DisciplinaDTO dto) {
        disciplinaValidator.validarCodigoUnico(dto.codigo(), null);
        Usuario responsavel = usuarioService.getUsuarioLogadoObrigatorio();
        Disciplina disciplina = DisciplinaMapper.toEntity(dto, responsavel);
        return DisciplinaMapper.toDto(disciplinaRepository.save(disciplina));
    }

    @Transactional
    public DisciplinaDTO update(Long id, DisciplinaDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Disciplina não encontrada"));

        disciplinaValidator.validarCodigoUnico(dto.codigo(), id);
        DisciplinaMapper.copyToEntity(dto, disciplina);
        return DisciplinaMapper.toDto(disciplinaRepository.save(disciplina));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            disciplinaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Não é possível remover a disciplina pois há matrículas associadas.");
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Disciplina não encontrada");
        }
    }
}
