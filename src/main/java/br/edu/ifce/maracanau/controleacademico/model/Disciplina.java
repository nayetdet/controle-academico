package br.edu.ifce.maracanau.controleacademico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "disciplinas")
public class Disciplina extends BaseModel {

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false)
    private String semestre;

}
