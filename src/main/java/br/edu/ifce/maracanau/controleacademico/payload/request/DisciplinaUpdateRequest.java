package br.edu.ifce.maracanau.controleacademico.payload.request;

public record DisciplinaUpdateRequest(
        String nome,
        Integer cargaHoraria,
        String semestre
) {
}
