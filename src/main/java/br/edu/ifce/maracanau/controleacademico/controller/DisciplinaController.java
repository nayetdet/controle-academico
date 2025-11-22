package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        List<DisciplinaDTO> disciplinas = disciplinaService.findAll();
        model.addAttribute("disciplinas", disciplinas);
        return "disciplinas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("disciplina", new DisciplinaDTO(null, "", "", 0, "", null));
        return "disciplinas/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("disciplina") DisciplinaDTO disciplinaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "disciplinas/form";
        }
        try {
            disciplinaService.create(disciplinaDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina cadastrada com sucesso.");
            return "redirect:/disciplinas";
        } catch (IllegalStateException ex) {
            result.rejectValue("codigo", "codigo", ex.getMessage());
            return "disciplinas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("disciplina", disciplinaService.findById(id));
            return "disciplinas/form";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/disciplinas";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=put")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("disciplina") DisciplinaDTO disciplinaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "disciplinas/form";
        }
        try {
            disciplinaService.update(id, disciplinaDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina atualizada com sucesso.");
            return "redirect:/disciplinas";
        } catch (IllegalStateException ex) {
            result.rejectValue("codigo", "codigo", ex.getMessage());
            return "disciplinas/form";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/disciplinas";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=delete")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            disciplinaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina removida com sucesso.");
        } catch (IllegalStateException | NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/disciplinas";
    }

}
