package br.edu.ifce.maracanau.controleacademico.payload.query;

import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;

import java.time.LocalDate;
import java.util.Map;

public class MatriculaDisciplinaQuery extends BaseQuery {

    private String matriculaAluno;
    private String codigoDisciplina;
    private LocalDate dataMatricula;
    private SituacaoMatricula situacao;
    private Double notaFinalMinima;
    private Double notaFinalMaxima;

    protected MatriculaDisciplinaQuery(Integer pageNumber, Integer pageSize, String orderBy) {
        super(
                Map.of(
                        "id", "id",
                        "matriculaAluno", "aluno.matricula",
                        "codigoDisciplina", "disciplina.codigo",
                        "situacao", "situacao",
                        "notaFinal", "notaFinal"
                ),
                pageNumber,
                pageSize,
                orderBy
        );
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Double getNotaFinalMinima() {
        return notaFinalMinima;
    }

    public void setNotaFinalMinima(Double notaFinalMinima) {
        this.notaFinalMinima = notaFinalMinima;
    }

    public Double getNotaFinalMaxima() {
        return notaFinalMaxima;
    }

    public void setNotaFinalMaxima(Double notaFinalMaxima) {
        this.notaFinalMaxima = notaFinalMaxima;
    }

}
