package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.MatriculaAlunoAndDisciplinaConflictException;
import br.edu.ifce.maracanau.controleacademico.exception.MatriculaDisciplinaNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.MatriculaMapper;
import br.edu.ifce.maracanau.controleacademico.model.Matricula;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.MatriculaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.MatriculaDisciplinaValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final MatriculaMapper matriculaMapper;
    private final MatriculaDisciplinaValidator matriculaDisciplinaValidator;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            MatriculaMapper matriculaMapper,
            MatriculaDisciplinaValidator matriculaDisciplinaValidator
    ) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.matriculaDisciplinaValidator = matriculaDisciplinaValidator;
    }

    @Transactional(readOnly = true)
    public ApplicationPage<MatriculaDTO> search(MatriculaQuery query) {
        Page<Matricula> page = matriculaRepository.search(
                query.getMatriculaAluno(),
                query.getCodigoDisciplina(),
                query.getDataMatricula(),
                query.getSituacao(),
                query.getNotaFinalMinima(),
                query.getNotaFinalMaxima(),
                query.getPageable()
        );

        return new ApplicationPage<>(page.map(matriculaMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<MatriculaDTO> findByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        return matriculaRepository
                .findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)
                .map(matriculaMapper::toDTO);
    }

    @Transactional
    public MatriculaDTO create(MatriculaRequest request, Usuario usuarioLogado) {
        matriculaDisciplinaValidator.validateAlunoMatriculaAndDisciplinaCodigo(request.matriculaAluno(), request.codigoDisciplina());
        Matricula matricula = matriculaRepository.save(matriculaMapper.toModel(request, usuarioLogado));
        return matriculaMapper.toDTO(matricula);
    }

    @Transactional
    public MatriculaDTO update(String matriculaAluno, String codigoDisciplina, MatriculaUpdateRequest request) {
        Matricula matricula = matriculaRepository.
                findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)
                .orElseThrow(MatriculaAlunoAndDisciplinaConflictException::new);

        matriculaDisciplinaValidator.validateAlunoMatriculaAndDisciplinaCodigoOnUpdate(matricula.getId(), matriculaAluno, codigoDisciplina);
        matriculaMapper.update(matricula, request);
        matricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDTO(matricula);
    }

    @Transactional
    public void deleteByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        if (!matriculaRepository.existsByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)) {
            throw new MatriculaDisciplinaNotFoundException();
        }

        matriculaRepository.deleteByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
    }

}
