package br.edu.ifce.maracanau.controleacademico.payload.request;

public record UsuarioLoginRequest(
        String login,
        String senha
) {
}
