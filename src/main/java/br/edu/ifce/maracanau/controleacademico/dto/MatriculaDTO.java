package br.edu.ifce.maracanau.controleacademico.dto;

import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record MatriculaDTO(
        Long id,

        @NotNull(message = "Aluno é obrigatório")
        Long alunoId,

        String alunoNome,

        @NotNull(message = "Disciplina é obrigatória")
        Long disciplinaId,

        String disciplinaNome,

        @NotNull(message = "Data da matrícula é obrigatória")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataMatricula,

        @NotNull(message = "Situação é obrigatória")
        SituacaoMatricula situacao,

        @PositiveOrZero(message = "Nota precisa ser maior ou igual a zero")
        Double notaFinal,

        String responsavelLogin
) {
}
