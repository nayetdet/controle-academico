package br.edu.ifce.maracanau.controleacademico.controller.api;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.DisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.security.annotation.PreAuthorizeSecretario;
import br.edu.ifce.maracanau.controleacademico.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@PreAuthorizeSecretario
@RestController
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaRestController {

    private final DisciplinaService disciplinaService;

    public DisciplinaRestController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public ResponseEntity<ApplicationPage<DisciplinaDTO>> searchDisciplinas(@ParameterObject @Valid DisciplinaQuery query) {
        ApplicationPage<DisciplinaDTO> responses = disciplinaService.search(query);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{codigoDisciplina}")
    public ResponseEntity<DisciplinaDTO> findDisciplinaByCodigo(@PathVariable String codigoDisciplina) {
        Optional<DisciplinaDTO> response = disciplinaService.findByCodigo(codigoDisciplina);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> create(@RequestBody @Valid DisciplinaRequest request, @AuthenticationPrincipal Usuario usuarioLogado) {
        DisciplinaDTO response = disciplinaService.create(request, usuarioLogado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{codigoDisciplina}")
                .buildAndExpand(response.codigo())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{codigoDisciplina}")
    public ResponseEntity<DisciplinaDTO> update(@PathVariable String codigoDisciplina, @RequestBody @Valid DisciplinaUpdateRequest request) {
        DisciplinaDTO response = disciplinaService.update(codigoDisciplina, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{codigoDisciplina}")
    public ResponseEntity<Void> deleteByCodigo(@PathVariable String codigoDisciplina) {
        disciplinaService.deleteByCodigo(codigoDisciplina);
        return ResponseEntity.noContent().build();
    }

}
