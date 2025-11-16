package br.edu.ifce.maracanau.controleacademico.payload.request;

import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;

import java.time.LocalDate;

public record AlunoUpdateRequest(
        String nome,
        String email,
        LocalDate dataNascimento,
        StatusAluno status
) {
}
