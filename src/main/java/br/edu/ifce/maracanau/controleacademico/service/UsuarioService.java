package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario create(String login, String senha, PerfilUsuario perfil, Usuario responsavel) {
        return usuarioRepository.findByLogin(login)
                .orElseGet(() -> {
                    Usuario usuario = new Usuario();
                    usuario.setResponsavel(responsavel);
                    usuario.setLogin(login);
                    usuario.setSenha(passwordEncoder.encode(senha));
                    usuario.setPerfil(perfil);
                    return usuarioRepository.save(usuario);
                });
    }

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String login = authentication.getName();
        return usuarioRepository.findByLogin(login).orElse(null);
    }

    public Usuario getUsuarioLogadoObrigatorio() {
        Usuario usuario = getUsuarioLogado();
        if (usuario == null) {
            throw new IllegalStateException("Usuário autenticado não encontrado para registrar responsável.");
        }

        return usuario;
    }

}
