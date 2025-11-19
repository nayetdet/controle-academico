package br.edu.ifce.maracanau.controleacademico.controller.api;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.UsuarioDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.UsuarioQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.UsuarioUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.security.annotation.PreAuthorizeAdmin;
import br.edu.ifce.maracanau.controleacademico.service.UsuarioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@PreAuthorizeAdmin
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<ApplicationPage<UsuarioDTO>> searchUsuarios(@ParameterObject @Valid UsuarioQuery query) {
        ApplicationPage<UsuarioDTO> responses = usuarioService.search(query);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{login}")
    public ResponseEntity<UsuarioDTO> findUsuarioByLogin(@PathVariable String login) {
        Optional<UsuarioDTO> response = usuarioService.findByLogin(login);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{login}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable String login, @RequestBody @Valid UsuarioUpdateRequest request, @AuthenticationPrincipal Usuario usuarioLogado) {
        UsuarioDTO response = usuarioService.update(login, request, usuarioLogado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteByLogin(@PathVariable String login, @AuthenticationPrincipal Usuario usuarioLogado) {
        usuarioService.deleteByLogin(login, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

}
