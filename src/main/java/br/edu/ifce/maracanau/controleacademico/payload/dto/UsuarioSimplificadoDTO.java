package br.edu.ifce.maracanau.controleacademico.payload.dto;

import br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario;

public record UsuarioSimplificadoDTO(
        Long id,
        String login,
        PerfilUsuario perfil
) {
}
