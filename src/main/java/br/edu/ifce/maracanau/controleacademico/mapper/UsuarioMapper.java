package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioDTO;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioSimplificadoDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRegistrationRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toModel(UsuarioRegistrationRequest request, Usuario responsavel, String senhaCriptografada) {
        return new Usuario(
                responsavel,
                request.login(),
                senhaCriptografada,
                request.perfil()
        );
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil(),
                toSimplificadoDTO(usuario.getResponsavel())
        );
    }

    public UsuarioSimplificadoDTO toSimplificadoDTO(Usuario usuario) {
        return new UsuarioSimplificadoDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }

    public void update(Usuario usuario, UsuarioUpdateRequest request, String senhaCriptografada) {
        usuario.setLogin(request.login());
        usuario.setSenha(senhaCriptografada);
    }

}
