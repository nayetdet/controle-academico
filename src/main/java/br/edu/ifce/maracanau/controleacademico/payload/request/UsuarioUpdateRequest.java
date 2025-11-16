package br.edu.ifce.maracanau.controleacademico.payload.request;

public record UsuarioUpdateRequest(
        String login,
        String senha
) {
}
