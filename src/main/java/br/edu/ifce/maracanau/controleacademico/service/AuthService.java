package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.exception.UsuarioNotFoundException;
import br.edu.ifce.maracanau.controleacademico.mapper.UsuarioMapper;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.JwtTokenDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioLoginRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRefreshTokenRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRegistrationRequest;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import br.edu.ifce.maracanau.controleacademico.validator.UsuarioValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

    public AuthService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            UsuarioValidator usuarioValidator
    ) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.usuarioValidator = usuarioValidator;
    }

    @Transactional(readOnly = true)
    public JwtTokenDTO login(UsuarioLoginRequest request) {
        Usuario usuario = usuarioRepository.findByLogin(request.login()).orElseThrow(UsuarioNotFoundException::new);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.senha()));
        return jwtTokenService.accessToken(usuario.getUsername(), usuario.getRoles());
    }

    @Transactional
    public JwtTokenDTO register(UsuarioRegistrationRequest request, Usuario usuarioLogado) {
        usuarioValidator.validateLogin(request.login());
        Usuario usuario = usuarioRepository.save(usuarioMapper.toModel(request, usuarioLogado, passwordEncoder.encode(request.senha())));
        return jwtTokenService.accessToken(usuario.getUsername(), usuario.getRoles());
    }

    @Transactional(readOnly = true)
    public JwtTokenDTO refresh(UsuarioRefreshTokenRequest request) {
        if (usuarioRepository.existsByLogin(request.login())) {
            throw new UsuarioNotFoundException();
        }

        return jwtTokenService.refreshToken(request.refreshToken());
    }

}
