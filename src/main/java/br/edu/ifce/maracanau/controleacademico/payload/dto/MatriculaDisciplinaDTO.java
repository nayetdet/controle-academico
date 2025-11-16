package br.edu.ifce.maracanau.controleacademico.payload.dto;

import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;

import java.time.LocalDate;

public record MatriculaDisciplinaDTO(
        Long id,
        AlunoDTO aluno,
        DisciplinaDTO disciplina,
        LocalDate dataMatricula,
        SituacaoMatricula situacao,
        Double notaFinal,
        UsuarioSimplificadoDTO responsavel
) {
}
