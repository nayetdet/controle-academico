package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.mapper.MatriculaMapper;
import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.model.Matricula;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.MatriculaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioService usuarioService;
    private final MatriculaValidator matriculaValidator;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            AlunoRepository alunoRepository,
            DisciplinaRepository disciplinaRepository,
            UsuarioService usuarioService,
            MatriculaValidator matriculaValidator
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.usuarioService = usuarioService;
        this.matriculaValidator = matriculaValidator;
    }

    @Transactional(readOnly = true)
    public List<MatriculaDTO> findAll() {
        return matriculaRepository.findAll().stream()
                .map(MatriculaMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaDTO findById(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matrícula não encontrada"));
        return MatriculaMapper.toDto(matricula);
    }

    @Transactional
    public MatriculaDTO create(MatriculaDTO dto) {
        matriculaValidator.validarDuplicidade(dto.alunoId(), dto.disciplinaId(), null);
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new NoSuchElementException("Disciplina não encontrada"));
        Usuario responsavel = usuarioService.getUsuarioLogadoObrigatorio();
        Matricula matricula = MatriculaMapper.toEntity(dto, aluno, disciplina, responsavel);
        return MatriculaMapper.toDto(matriculaRepository.save(matricula));
    }

    @Transactional
    public MatriculaDTO update(Long id, MatriculaDTO dto) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matrícula não encontrada"));

        matriculaValidator.validarDuplicidade(dto.alunoId(), dto.disciplinaId(), id);
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new NoSuchElementException("Disciplina não encontrada"));

        MatriculaMapper.copyToEntity(dto, matricula, aluno, disciplina);
        return MatriculaMapper.toDto(matriculaRepository.save(matricula));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            matriculaRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Matrícula não encontrada");
        }
    }

}
