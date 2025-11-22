package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;
import br.edu.ifce.maracanau.controleacademico.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<AlunoDTO> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String createForm(Model model) {
        model.addAttribute("aluno", new AlunoDTO(null, "", "", "", null, StatusAluno.ATIVO, null));
        adicionarOpcoes(model);
        return "alunos/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("aluno") AlunoDTO alunoDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "alunos/form";
        }
        try {
            alunoService.create(alunoDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno cadastrado com sucesso.");
            return "redirect:/alunos";
        } catch (IllegalStateException ex) {
            result.rejectValue("matricula", "matricula", ex.getMessage());
            adicionarOpcoes(model);
            return "alunos/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("aluno", alunoService.findById(id));
            adicionarOpcoes(model);
            return "alunos/form";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/alunos";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=put")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("aluno") AlunoDTO alunoDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "alunos/form";
        }
        try {
            alunoService.update(id, alunoDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno atualizado com sucesso.");
            return "redirect:/alunos";
        } catch (IllegalStateException ex) {
            result.rejectValue("matricula", "matricula", ex.getMessage());
            adicionarOpcoes(model);
            return "alunos/form";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/alunos";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno removido com sucesso.");
        } catch (IllegalStateException | NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/alunos";
    }

    private void adicionarOpcoes(Model model) {
        model.addAttribute("statusOptions", StatusAluno.values());
    }

}
