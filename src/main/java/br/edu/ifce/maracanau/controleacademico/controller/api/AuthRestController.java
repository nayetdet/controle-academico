package br.edu.ifce.maracanau.controleacademico.controller.api;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.JwtTokenDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioLoginRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRefreshTokenRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioRegistrationRequest;
import br.edu.ifce.maracanau.controleacademico.security.annotation.PreAuthorizeAdmin;
import br.edu.ifce.maracanau.controleacademico.security.annotation.PreAuthorizeAnonymous;
import br.edu.ifce.maracanau.controleacademico.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorizeAnonymous
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid UsuarioLoginRequest request) {
        JwtTokenDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorizeAdmin
    @PostMapping("/register")
    public ResponseEntity<JwtTokenDTO> register(@RequestBody @Valid UsuarioRegistrationRequest request, @AuthenticationPrincipal Usuario usuarioLogado) {
        JwtTokenDTO response = authService.register(request, usuarioLogado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/usuarios/{login}")
                .buildAndExpand(response.login())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PreAuthorizeAnonymous
    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDTO> refresh(@RequestBody @Valid UsuarioRefreshTokenRequest request) {
        JwtTokenDTO response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }

}
