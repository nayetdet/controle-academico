package br.edu.ifce.maracanau.controleacademico.controller;

import br.edu.ifce.maracanau.controleacademico.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.dto.AlunoDTO;
import br.edu.ifce.maracanau.controleacademico.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;
import br.edu.ifce.maracanau.controleacademico.service.AlunoService;
import br.edu.ifce.maracanau.controleacademico.service.DisciplinaService;
import br.edu.ifce.maracanau.controleacademico.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping
    public String listar(Model model) {
        List<MatriculaDTO> matriculas = matriculaService.findAll();
        model.addAttribute("matriculas", matriculas);
        return "matriculas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        MatriculaDTO dto = new MatriculaDTO(null, null, null, null, null, LocalDate.now(), SituacaoMatricula.CURSANDO, null, null);
        model.addAttribute("matricula", dto);
        adicionarOpcoes(model);
        return "matriculas/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("matricula") MatriculaDTO matriculaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "matriculas/form";
        }
        try {
            matriculaService.create(matriculaDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Matrícula cadastrada com sucesso.");
            return "redirect:/matriculas";
        } catch (IllegalStateException ex) {
            result.reject(null, ex.getMessage());
            adicionarOpcoes(model);
            return "matriculas/form";
        } catch (NoSuchElementException ex) {
            result.reject(null, ex.getMessage());
            adicionarOpcoes(model);
            return "matriculas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("matricula", matriculaService.findById(id));
            adicionarOpcoes(model);
            return "matriculas/form";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/matriculas";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=put")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("matricula") MatriculaDTO matriculaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "matriculas/form";
        }
        try {
            matriculaService.update(id, matriculaDTO);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Matrícula atualizada com sucesso.");
            return "redirect:/matriculas";
        } catch (IllegalStateException ex) {
            result.reject(null, ex.getMessage());
            adicionarOpcoes(model);
            return "matriculas/form";
        } catch (NoSuchElementException ex) {
            result.reject(null, ex.getMessage());
            adicionarOpcoes(model);
            return "matriculas/form";
        }
    }

    @PostMapping(path = "/{id}", params = "_method=delete")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            matriculaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Matrícula removida com sucesso.");
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/matriculas";
    }

    private void adicionarOpcoes(Model model) {
        List<AlunoDTO> alunos = alunoService.findAll();
        List<DisciplinaDTO> disciplinas = disciplinaService.findAll();
        model.addAttribute("alunos", alunos);
        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("situacoes", SituacaoMatricula.values());
    }

}
