package br.edu.ifce.maracanau.controleacademico.dto;

import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AlunoDTO(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Matrícula é obrigatória")
        String matricula,

        @NotNull(message = "Data de nascimento é obrigatória")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataNascimento,

        @NotNull(message = "Status é obrigatório")
        StatusAluno status,

        String responsavelLogin
) {
}
