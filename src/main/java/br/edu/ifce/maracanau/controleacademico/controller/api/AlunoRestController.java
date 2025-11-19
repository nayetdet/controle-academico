package br.edu.ifce.maracanau.controleacademico.controller.api;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.AlunoQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.MatriculaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.page.ApplicationPage;
import br.edu.ifce.maracanau.controleacademico.payload.request.*;
import br.edu.ifce.maracanau.controleacademico.security.annotation.PreAuthorizeSecretario;
import br.edu.ifce.maracanau.controleacademico.service.AlunoService;
import br.edu.ifce.maracanau.controleacademico.service.MatriculaService;
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
@RequestMapping("/api/v1/alunos")
public class AlunoRestController {

    private final AlunoService alunoService;
    private final MatriculaService matriculaService;

    public AlunoRestController(AlunoService alunoService, MatriculaService matriculaService) {
        this.alunoService = alunoService;
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public ResponseEntity<ApplicationPage<AlunoDTO>> searchAlunos(@ParameterObject @Valid AlunoQuery query) {
        ApplicationPage<AlunoDTO> responses = alunoService.search(query);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/matriculas")
    public ResponseEntity<ApplicationPage<MatriculaDTO>> searchMatriculas(@ParameterObject @Valid MatriculaQuery query) {
        ApplicationPage<MatriculaDTO> responses = matriculaService.search(query);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{matriculaAluno}")
    public ResponseEntity<AlunoDTO> findAlunoByMatricula(@PathVariable String matriculaAluno) {
        Optional<AlunoDTO> response = alunoService.findByMatricula(matriculaAluno);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{matriculaAluno}/matriculas/{codigoDisciplina}")
    public ResponseEntity<MatriculaDTO> findMatriculaByAlunoMatriculaAndDisciplinaCodigo(@PathVariable String matriculaAluno, @PathVariable String codigoDisciplina) {
        Optional<MatriculaDTO> response = matriculaService.findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> create(@RequestBody @Valid AlunoRequest request, @AuthenticationPrincipal Usuario usuarioLogado) {
        AlunoDTO response = alunoService.create(request, usuarioLogado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{matriculaAluno}")
                .buildAndExpand(response.matricula())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/matriculas")
    public ResponseEntity<MatriculaDTO> createMatricula(@RequestBody @Valid MatriculaRequest request, @AuthenticationPrincipal Usuario usuarioLogado) {
        MatriculaDTO response = matriculaService.create(request, usuarioLogado);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{matriculaAluno}/matriculas/{codigoDisciplina}")
                .buildAndExpand(response.aluno().matricula(), response.disciplina().codigo())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{matriculaAluno}")
    public ResponseEntity<AlunoDTO> update(@PathVariable String matriculaAluno, @RequestBody @Valid AlunoUpdateRequest request) {
        AlunoDTO response = alunoService.update(matriculaAluno, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{matriculaAluno}/matriculas/{codigoDisciplina}")
    public ResponseEntity<MatriculaDTO> updateMatricula(@PathVariable String matriculaAluno, @PathVariable String codigoDisciplina, @RequestBody @Valid MatriculaUpdateRequest request) {
        MatriculaDTO response = matriculaService.update(matriculaAluno, codigoDisciplina, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{matriculaAluno}")
    public ResponseEntity<Void> deleteByMatricula(@PathVariable String matriculaAluno) {
        alunoService.deleteByMatricula(matriculaAluno);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matriculaAluno}/matriculas/{codigoDisciplina}")
    public ResponseEntity<Void> deleteMatriculaByAlunoMatriculaAndDisciplinaCodigo(@PathVariable String matriculaAluno, @PathVariable String codigoDisciplina) {
        matriculaService.deleteByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
        return ResponseEntity.noContent().build();
    }

}
