package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.exception.BaseException;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.query.DisciplinaQuery;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.service.DisciplinaService;
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
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            Model model
    ) {
        DisciplinaQuery query = new DisciplinaQuery(page, size, orderBy, null, null, null, null, null);
        var disciplinasPage = disciplinaService.search(query);
        model.addAttribute("disciplinas", disciplinasPage.content());
        model.addAttribute("pageInfo", disciplinasPage.pageable());
        return "disciplinas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("disciplinaRequest", new DisciplinaRequest(null, null, null, null));
        model.addAttribute("modoEdicao", false);
        return "disciplinas/form";
    }

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("disciplinaRequest") DisciplinaRequest disciplinaRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal Usuario responsavel,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            return "disciplinas/form";
        }

        try {
            disciplinaService.create(disciplinaRequest, responsavel);
            redirectAttributes.addFlashAttribute("successMessage", "Disciplina criada com sucesso.");
            return "redirect:/disciplinas";
        } catch (BaseException exception) {
            bindingResult.rejectValue("codigo", "error.disciplina", exception.getMessage());
            model.addAttribute("modoEdicao", false);
            return "disciplinas/form";
        }
    }

    @GetMapping("/{codigo}/editar")
    public String editar(@PathVariable String codigo, Model model, RedirectAttributes redirectAttributes) {
        Optional<DisciplinaDTO> disciplinaDTO = disciplinaService.findByCodigo(codigo);
        if (disciplinaDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Disciplina não encontrada.");
            return "redirect:/disciplinas";
        }

        DisciplinaDTO dto = disciplinaDTO.get();
        model.addAttribute("disciplinaUpdateRequest", new DisciplinaUpdateRequest(dto.nome(), dto.cargaHoraria(), dto.semestre()));
        model.addAttribute("codigoDisciplina", dto.codigo());
        model.addAttribute("modoEdicao", true);
        return "disciplinas/form";
    }

    @PostMapping("/{codigo}")
    public String atualizar(
            @PathVariable String codigo,
            @Valid @ModelAttribute("disciplinaUpdateRequest") DisciplinaUpdateRequest disciplinaUpdateRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", true);
            model.addAttribute("codigoDisciplina", codigo);
            return "disciplinas/form";
        }

        try {
            disciplinaService.update(codigo, disciplinaUpdateRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Disciplina atualizada com sucesso.");
            return "redirect:/disciplinas";
        } catch (BaseException exception) {
            bindingResult.reject("error.disciplina", exception.getMessage());
            model.addAttribute("modoEdicao", true);
            model.addAttribute("codigoDisciplina", codigo);
            return "disciplinas/form";
        }
    }

    @PostMapping("/{codigo}/excluir")
    public String excluir(@PathVariable String codigo, RedirectAttributes redirectAttributes) {
        try {
            disciplinaService.deleteByCodigo(codigo);
            redirectAttributes.addFlashAttribute("successMessage", "Disciplina removida com sucesso.");
        } catch (DataIntegrityViolationException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível remover a disciplina pois existem matrículas vinculadas.");
        } catch (BaseException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }

        return "redirect:/disciplinas";
    }
}
