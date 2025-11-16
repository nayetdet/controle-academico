package br.edu.ifce.maracanau.controleacademico.payload.dto;

import java.time.LocalDateTime;

public record JwtTokenDTO(
        String login,
        String accessToken,
        String refreshToken,
        LocalDateTime emitidoEm,
        LocalDateTime accessExpiraEm,
        LocalDateTime refreshExpiraEm
) {
}
