package br.edu.ifce.maracanau.controleacademico.payload.request;

import br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario;

public record UsuarioRegistrationRequest(
        String login,
        String senha,
        PerfilUsuario perfil
) {
}
