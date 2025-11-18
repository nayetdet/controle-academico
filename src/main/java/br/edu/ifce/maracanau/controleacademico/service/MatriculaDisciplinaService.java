package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException;
import br.edu.ifce.maracanau.controleacademico.exception.MatriculaDisciplinaNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.MatriculaDisciplinaMapper;
import br.edu.ifce.maracanau.controleacademico.model.MatriculaDisciplina;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.MatriculaDisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaDisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaDisciplinaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.MatriculaDisciplinaRepository;
import br.edu.ifce.maracanau.controleacademico.validator.MatriculaDisciplinaValidator;
import org.springframework.data.domain.Page;
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
        Page<MatriculaDisciplina> page = matriculaDisciplinaRepository.search(
                query.getMatriculaAluno(),
                query.getCodigoDisciplina(),
                query.getDataMatricula(),
                query.getSituacao(),
                query.getNotaFinalMinima(),
                query.getNotaFinalMaxima(),
                query.getPageable()
        );

        return new ApplicationPage<>(page.map(matriculaDisciplinaMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<MatriculaDisciplinaDTO> findByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        return matriculaDisciplinaRepository
                .findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)
                .map(matriculaDisciplinaMapper::toDTO);
    }

    @Transactional
    public MatriculaDisciplinaDTO create(MatriculaDisciplinaRequest request, Usuario usuarioLogado) {
        matriculaDisciplinaValidator.validateAlunoMatriculaAndDisciplinaCodigo(request.matriculaAluno(), request.codigoDisciplina());
        MatriculaDisciplina matriculaDisciplina = matriculaDisciplinaRepository.save(matriculaDisciplinaMapper.toModel(request, usuarioLogado));
        return matriculaDisciplinaMapper.toDTO(matriculaDisciplina);
    }

    @Transactional
    public MatriculaDisciplinaDTO update(String matriculaAluno, String codigoDisciplina, MatriculaDisciplinaUpdateRequest request) {
        MatriculaDisciplina matriculaDisciplina = matriculaDisciplinaRepository.
                findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)
                .orElseThrow(MatriculaDisciplinaAlunoMatriculaAndDisciplinaCodigoConflictException::new);

        matriculaDisciplinaValidator.validateAlunoMatriculaAndDisciplinaCodigoOnUpdate(matriculaDisciplina.getId(), matriculaAluno, codigoDisciplina);
        matriculaDisciplinaMapper.update(matriculaDisciplina, request);
        matriculaDisciplina = matriculaDisciplinaRepository.save(matriculaDisciplina);
        return matriculaDisciplinaMapper.toDTO(matriculaDisciplina);
    }

    @Transactional
    public void deleteByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina) {
        if (!matriculaDisciplinaRepository.existsByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina)) {
            throw new MatriculaDisciplinaNotFoundException();
        }

        matriculaDisciplinaRepository.deleteByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
    }

}
