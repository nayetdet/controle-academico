package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.exception.BaseException;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;
import br.edu.ifce.maracanau.controleacademico.payload.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.AlunoQuery;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.AlunoUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.service.AlunoService;
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

import java.util.Optional;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @ModelAttribute("statusAlunos")
    public StatusAluno[] statusAlunos() {
        return StatusAluno.values();
    }

    @GetMapping
    public String listar(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            Model model
    ) {
        AlunoQuery query = new AlunoQuery(page, size, orderBy, null, null, null, null, null);
        var alunosPage = alunoService.search(query);
        model.addAttribute("alunos", alunosPage.content());
        model.addAttribute("pageInfo", alunosPage.pageable());
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("alunoRequest", new AlunoRequest(null, null, null, null, null));
        model.addAttribute("modoEdicao", false);
        return "alunos/form";
    }

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("alunoRequest") AlunoRequest alunoRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal Usuario usuarioLogado,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            return "alunos/form";
        }

        try {
            alunoService.create(alunoRequest, usuarioLogado);
            redirectAttributes.addFlashAttribute("successMessage", "Aluno cadastrado com sucesso.");
            return "redirect:/alunos";
        } catch (BaseException exception) {
            bindingResult.rejectValue("matricula", "error.aluno", exception.getMessage());
            model.addAttribute("modoEdicao", false);
            return "alunos/form";
        }
    }

    @GetMapping("/{matricula}/editar")
    public String editar(@PathVariable String matricula, Model model, RedirectAttributes redirectAttributes) {
        Optional<AlunoDTO> alunoDTO = alunoService.findByMatricula(matricula);
        if (alunoDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Aluno não encontrado.");
            return "redirect:/alunos";
        }

        AlunoDTO dto = alunoDTO.get();
        model.addAttribute("alunoUpdateRequest", new AlunoUpdateRequest(dto.nome(), dto.email(), dto.dataNascimento(), dto.status()));
        model.addAttribute("matriculaAluno", dto.matricula());
        model.addAttribute("modoEdicao", true);
        return "alunos/form";
    }

    @PostMapping("/{matricula}")
    public String atualizar(
            @PathVariable String matricula,
            @Valid @ModelAttribute("alunoUpdateRequest") AlunoUpdateRequest alunoUpdateRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", true);
            model.addAttribute("matriculaAluno", matricula);
            return "alunos/form";
        }

        try {
            alunoService.update(matricula, alunoUpdateRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Dados do aluno atualizados.");
            return "redirect:/alunos";
        } catch (BaseException exception) {
            bindingResult.reject("error.aluno", exception.getMessage());
            model.addAttribute("modoEdicao", true);
            model.addAttribute("matriculaAluno", matricula);
            return "alunos/form";
        }
    }

    @PostMapping("/{matricula}/excluir")
    public String excluir(@PathVariable String matricula, RedirectAttributes redirectAttributes) {
        try {
            alunoService.deleteByMatricula(matricula);
            redirectAttributes.addFlashAttribute("successMessage", "Aluno removido com sucesso.");
        } catch (DataIntegrityViolationException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível remover o aluno pois existem matrículas vinculadas.");
        } catch (BaseException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }

        return "redirect:/alunos";
    }
}
