package br.edu.ifce.maracanau.controleacademico.model;

import br.edu.ifce.maracanau.controleacademico.model.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "alunos")
public class Aluno extends BaseModel {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

}
