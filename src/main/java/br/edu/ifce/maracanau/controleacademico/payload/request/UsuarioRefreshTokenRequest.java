package br.edu.ifce.maracanau.controleacademico.payload.request;

public record UsuarioRefreshTokenRequest(
        String login,
        String refreshToken
) {
}
