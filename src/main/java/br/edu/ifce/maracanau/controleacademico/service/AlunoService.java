package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.AlunoNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.AlunoMapper;
import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.AlunoQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import br.edu.ifce.maracanau.controleacademico.validator.AlunoValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;
    private final AlunoValidator alunoValidator;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper, AlunoValidator alunoValidator) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.alunoValidator = alunoValidator;
    }

    @Transactional(readOnly = true)
    public ApplicationPage<AlunoDTO> search(AlunoQuery query) {
        Page<Aluno> page = alunoRepository.search(
            query.getNome(),
            query.getMatricula(),
            query.getDataNascimentoMinima(),
            query.getDataNascimentoMaxima(),
            query.getStatus(),
            query.getPageable()
        );

        return new ApplicationPage<>(page.map(alunoMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<AlunoDTO> findByMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula).map(alunoMapper::toDTO);
    }

    @Transactional
    public AlunoDTO create(AlunoRequest request, Usuario usuarioLogado) {
        alunoValidator.validateMatricula(request.matricula());
        Aluno aluno = alunoRepository.save(alunoMapper.toModel(request, usuarioLogado));
        return alunoMapper.toDTO(aluno);
    }

    @Transactional
    public AlunoDTO update(String matricula, AlunoUpdateRequest request) {
        Aluno aluno = alunoRepository.findByMatricula(matricula).orElseThrow(AlunoNotFoundException::new);
        alunoMapper.update(aluno, request);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    @Transactional
    public void deleteByMatricula(String matricula) {
        if (!alunoRepository.existsByMatricula(matricula)) {
            throw new AlunoNotFoundException();
        }

        alunoRepository.deleteByMatricula(matricula);
    }

}
