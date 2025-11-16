package br.edu.ifce.maracanau.controleacademico.model;

import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matricula_disciplinas")
public class MatriculaDisciplina extends BaseModel {

    public MatriculaDisciplina() {}

    public MatriculaDisciplina(
            Usuario responsavel,
            Aluno aluno,
            Disciplina disciplina,
            LocalDate dataMatricula,
            SituacaoMatricula situacao,
            Double notaFinal
    ) {
        super(responsavel);
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.dataMatricula = dataMatricula;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
    }

    public MatriculaDisciplina(
            Long id,
            Usuario responsavel,
            Aluno aluno,
            Disciplina disciplina,
            LocalDate dataMatricula,
            SituacaoMatricula situacao,
            Double notaFinal
    ) {
        super(id, responsavel);
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.dataMatricula = dataMatricula;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(nullable = false)
    private LocalDate dataMatricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoMatricula situacao;

    @Column
    private Double notaFinal;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

}
