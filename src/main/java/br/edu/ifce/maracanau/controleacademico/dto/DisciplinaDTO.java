package br.edu.ifce.maracanau.controleacademico.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisciplinaDTO(
        Long id,

        @NotBlank(message = "Código é obrigatório")
        String codigo,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Carga horária é obrigatória")
        @Min(value = 1, message = "Carga horária deve ser maior que zero")
        Integer cargaHoraria,

        @NotBlank(message = "Semestre é obrigatório")
        String semestre,

        String responsavelLogin
) {
}
