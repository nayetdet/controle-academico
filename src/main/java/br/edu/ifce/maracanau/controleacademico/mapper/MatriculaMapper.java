package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.model.Matricula;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;

public final class MatriculaMapper {

    private MatriculaMapper() {
    }

    public static MatriculaDTO toDto(Matricula matricula) {
        String responsavel = matricula.getResponsavel() != null ? matricula.getResponsavel().getLogin() : null;
        return new MatriculaDTO(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome(),
                matricula.getDisciplina().getId(),
                matricula.getDisciplina().getNome(),
                matricula.getDataMatricula(),
                matricula.getSituacao(),
                matricula.getNotaFinal(),
                responsavel
        );
    }

    public static Matricula toEntity(MatriculaDTO request, Aluno aluno, Disciplina disciplina, Usuario responsavel) {
        Matricula matricula = new Matricula();
        matricula.setResponsavel(responsavel);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setDataMatricula(request.dataMatricula());
        matricula.setSituacao(request.situacao());
        matricula.setNotaFinal(request.notaFinal());
        return matricula;
    }

    public static void copyToEntity(MatriculaDTO request, Matricula matricula, Aluno aluno, Disciplina disciplina) {
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setDataMatricula(request.dataMatricula());
        matricula.setSituacao(request.situacao());
        matricula.setNotaFinal(request.notaFinal());
    }

}
