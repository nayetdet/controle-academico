package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    private final AlunoRepository alunoRepository;

    public AlunoMapper(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno toModel(String matricula) {
        return alunoRepository.findByMatricula(matricula).orElse(null);
    }

    public Aluno toModel(AlunoRequest request, Usuario responsavel) {
        return new Aluno(
                responsavel,
                request.nome(),
                request.email(),
                request.matricula(),
                request.dataNascimento(),
                request.status()
        );
    }

    public AlunoDTO toDTO(Aluno aluno) {
        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getDataNascimento(),
                aluno.getStatus()
        );
    }

    public void update(Aluno aluno, AlunoUpdateRequest request) {
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setStatus(request.status());
    }

}
