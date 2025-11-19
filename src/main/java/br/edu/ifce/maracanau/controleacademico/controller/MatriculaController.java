package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.exception.BaseException;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.AlunoQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.DisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.query.MatriculaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.service.AlunoService;
import br.edu.ifce.maracanau.controleacademico.service.DisciplinaService;
import br.edu.ifce.maracanau.controleacademico.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaController(
            MatriculaService matriculaService,
            AlunoService alunoService,
            DisciplinaService disciplinaService
    ) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @ModelAttribute("situacoesMatricula")
    public SituacaoMatricula[] situacoesMatricula() {
        return SituacaoMatricula.values();
    }

    @GetMapping
    public String listar(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "-dataMatricula") String orderBy,
            Model model
    ) {
        MatriculaQuery query = new MatriculaQuery(page, size, orderBy, null, null, null, null, null, null);
        var matriculasPage = matriculaService.search(query);
        model.addAttribute("matriculas", matriculasPage.content());
        model.addAttribute("pageInfo", matriculasPage.pageable());
        return "matriculas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        MatriculaRequest novaMatricula = new MatriculaRequest(null, null, LocalDate.now(), SituacaoMatricula.CURSANDO, null);
        model.addAttribute("matriculaRequest", novaMatricula);
        model.addAttribute("modoEdicao", false);
        popularListas(model);
        return "matriculas/form";
    }

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("matriculaRequest") MatriculaRequest matriculaRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal Usuario responsavel,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            popularListas(model);
            return "matriculas/form";
        }

        try {
            matriculaService.create(matriculaRequest, responsavel);
            redirectAttributes.addFlashAttribute("successMessage", "Matrícula criada com sucesso.");
            return "redirect:/matriculas";
        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject("error.matricula", "Não foi possível salvar a matrícula (integridade dos dados).");
            model.addAttribute("modoEdicao", false);
            popularListas(model);
            return "matriculas/form";
        } catch (BaseException exception) {
            bindingResult.reject("error.matricula", exception.getMessage());
            model.addAttribute("modoEdicao", false);
            popularListas(model);
            return "matriculas/form";
        }
    }

    @GetMapping("/{matriculaAluno}/{codigoDisciplina}/editar")
    public String editar(
            @PathVariable String matriculaAluno,
            @PathVariable String codigoDisciplina,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<MatriculaDTO> matriculaDTO = matriculaService.findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
        if (matriculaDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Matrícula não encontrada.");
            return "redirect:/matriculas";
        }

        MatriculaDTO dto = matriculaDTO.get();
        model.addAttribute("matriculaSelecionada", dto);
        model.addAttribute("matriculaUpdateRequest", new MatriculaUpdateRequest(dto.dataMatricula(), dto.situacao(), dto.notaFinal()));
        model.addAttribute("modoEdicao", true);
        popularListas(model);
        return "matriculas/form";
    }

    @PostMapping("/{matriculaAluno}/{codigoDisciplina}")
    public String atualizar(
            @PathVariable String matriculaAluno,
            @PathVariable String codigoDisciplina,
            @Valid @ModelAttribute("matriculaUpdateRequest") MatriculaUpdateRequest matriculaUpdateRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", true);
            adicionarMatriculaSelecionada(model, matriculaAluno, codigoDisciplina);
            popularListas(model);
            return "matriculas/form";
        }

        try {
            matriculaService.update(matriculaAluno, codigoDisciplina, matriculaUpdateRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Matrícula atualizada com sucesso.");
            return "redirect:/matriculas";
        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject("error.matricula", "Não foi possível atualizar a matrícula (integridade dos dados).");
            model.addAttribute("modoEdicao", true);
            adicionarMatriculaSelecionada(model, matriculaAluno, codigoDisciplina);
            popularListas(model);
            return "matriculas/form";
        } catch (BaseException exception) {
            bindingResult.reject("error.matricula", exception.getMessage());
            model.addAttribute("modoEdicao", true);
            adicionarMatriculaSelecionada(model, matriculaAluno, codigoDisciplina);
            popularListas(model);
            return "matriculas/form";
        }
    }

    @PostMapping("/{matriculaAluno}/{codigoDisciplina}/excluir")
    public String excluir(
            @PathVariable String matriculaAluno,
            @PathVariable String codigoDisciplina,
            RedirectAttributes redirectAttributes
    ) {
        try {
            matriculaService.deleteByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
            redirectAttributes.addFlashAttribute("successMessage", "Matrícula removida com sucesso.");
        } catch (BaseException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }

        return "redirect:/matriculas";
    }

    private void popularListas(Model model) {
        var alunos = alunoService.search(new AlunoQuery(0, 100, "nome", null, null, null, null, null)).content();
        var disciplinas = disciplinaService.search(new DisciplinaQuery(0, 100, "nome", null, null, null, null, null)).content();
        model.addAttribute("alunos", alunos);
        model.addAttribute("disciplinas", disciplinas);
    }

    private void adicionarMatriculaSelecionada(Model model, String matriculaAluno, String codigoDisciplina) {
        Optional<MatriculaDTO> matriculaDTO = matriculaService.findByAlunoMatriculaAndDisciplinaCodigo(matriculaAluno, codigoDisciplina);
        matriculaDTO.ifPresent(dto -> model.addAttribute("matriculaSelecionada", dto));
    }
}
