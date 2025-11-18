package br.edu.ifce.maracanau.controleacademico.payload.request;

import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;

import java.time.LocalDate;

public record MatriculaDisciplinaUpdateRequest(
        LocalDate dataMatricula,
        SituacaoMatricula situacao,
        Double notaFinal
) {
}
