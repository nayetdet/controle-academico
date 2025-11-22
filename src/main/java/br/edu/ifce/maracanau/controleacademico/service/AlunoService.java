package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.mapper.AlunoMapper;
import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import br.edu.ifce.maracanau.controleacademico.validator.AlunoValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;
    private final AlunoValidator alunoValidator;

    public AlunoService(AlunoRepository alunoRepository, UsuarioService usuarioService, AlunoValidator alunoValidator) {
        this.alunoRepository = alunoRepository;
        this.usuarioService = usuarioService;
        this.alunoValidator = alunoValidator;
    }

    @Transactional(readOnly = true)
    public List<AlunoDTO> findAll() {
        return alunoRepository.findAll(Sort.by("nome").ascending()).stream()
                .map(AlunoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlunoDTO findById(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));
        return AlunoMapper.toDto(aluno);
    }

    @Transactional
    public AlunoDTO create(AlunoDTO dto) {
        alunoValidator.validarMatriculaUnica(dto.matricula(), null);
        Usuario responsavel = usuarioService.getUsuarioLogadoObrigatorio();
        Aluno aluno = AlunoMapper.toEntity(dto, responsavel);
        return AlunoMapper.toDto(alunoRepository.save(aluno));
    }

    @Transactional
    public AlunoDTO update(Long id, AlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));

        alunoValidator.validarMatriculaUnica(dto.matricula(), id);
        AlunoMapper.copyToEntity(dto, aluno);
        return AlunoMapper.toDto(alunoRepository.save(aluno));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            alunoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Não é possível remover o aluno pois existem matrículas associadas.");
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Aluno não encontrado");
        }
    }

}
