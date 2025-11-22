package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;

public final class AlunoMapper {

    private AlunoMapper() {
    }

    public static AlunoDTO toDto(Aluno aluno) {
        String responsavel = aluno.getResponsavel() != null ? aluno.getResponsavel().getLogin() : null;
        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getDataNascimento(),
                aluno.getStatus(),
                responsavel
        );
    }

    public static Aluno toEntity(AlunoDTO request, Usuario responsavel) {
        Aluno aluno = new Aluno();
        aluno.setResponsavel(responsavel);
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setMatricula(request.matricula());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setStatus(request.status());
        return aluno;
    }

    public static void copyToEntity(AlunoDTO request, Aluno aluno) {
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setMatricula(request.matricula());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setStatus(request.status());
    }

}
