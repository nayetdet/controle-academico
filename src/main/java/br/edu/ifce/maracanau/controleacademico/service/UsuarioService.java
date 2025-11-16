package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.UsuarioModificationForbiddenException;
import br.edu.ifce.maracanau.controleacademico.exception.UsuarioNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.UsuarioMapper;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.UsuarioQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import br.edu.ifce.maracanau.controleacademico.security.context.SecurityContextProvider;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional(readOnly = true)
    public ApplicationPage<UsuarioDTO> search(UsuarioQuery query) {
        Page<Usuario> page = usuarioRepository.findAll(query.getPageable());
        return new ApplicationPage<>(page.map(usuarioMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByLogin(String login) {
        return usuarioRepository.findByLogin(login).map(usuarioMapper::toDTO);
    }

    @Transactional
    public void deleteByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(UsuarioNotFoundException::new);
        SecurityContextProvider.assertAuthorization(usuario.getLogin(), UsuarioModificationForbiddenException.class);
        usuarioRepository.delete(usuario);
    }

}
