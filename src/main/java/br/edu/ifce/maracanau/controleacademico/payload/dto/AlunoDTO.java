package br.edu.ifce.maracanau.controleacademico.payload.dto;

import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;

import java.time.LocalDate;

public record AlunoDTO(
        Long id,
        String nome,
        String email,
        String matricula,
        LocalDate dataNascimento,
        StatusAluno status
) {
}
