package br.edu.ifce.maracanau.controleacademico.payload.dto;

public record DisciplinaDTO(
        Long id,
        String codigo,
        String nome,
        Integer cargaHoraria,
        String semestre
) {
}
