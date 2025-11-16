package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.UsuarioModificationForbiddenException;
import br.edu.ifce.maracanau.controleacademico.exception.UsuarioNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.UsuarioMapper;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.UsuarioQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import br.edu.ifce.maracanau.controleacademico.validator.UsuarioValidator;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, UsuarioValidator usuarioValidator) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.usuarioValidator = usuarioValidator;
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
    public UsuarioDTO update(String login, UsuarioUpdateRequest request, Usuario usuarioLogado) {
        usuarioValidator.validateLoginOnUpdate(login, request.login());
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(UsuarioNotFoundException::new);
        usuarioMapper.update(usuario, request, passwordEncoder.encode(request.senha()));
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public void deleteByLogin(String login, Usuario usuarioLogado) {
        if (!login.equals(usuarioLogado.getLogin())) {
            throw new UsuarioModificationForbiddenException();
        }

        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(UsuarioNotFoundException::new);
        usuarioRepository.delete(usuario);
    }

}
