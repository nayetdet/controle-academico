package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRegistrationRequest;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final UsuarioRepository usuarioRepository;

    public UsuarioMapper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario toModel(String login) {
        return usuarioRepository.findByLogin(login).orElse(null);
    }

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
                usuario.getPerfil()
        );
    }

}
