package br.edu.ifce.maracanau.controleacademico.payload.query;

import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;

import java.time.LocalDate;
import java.util.Map;

public class AlunoQuery extends BaseQuery {

    private String nome;
    private String matricula;
    private LocalDate dataNascimentoMinima;
    private LocalDate dataNascimentoMaxima;
    private StatusAluno status;

    protected AlunoQuery(Integer pageNumber, Integer pageSize, String orderBy) {
        super(
                Map.of(
                        "id", "id",
                        "nome", "nome",
                        "dataNascimento", "dataNascimento",
                        "status", "status"
                ),
                pageNumber,
                pageSize,
                orderBy
        );
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataNascimentoMinima() {
        return dataNascimentoMinima;
    }

    public void setDataNascimentoMinima(LocalDate dataNascimentoMinima) {
        this.dataNascimentoMinima = dataNascimentoMinima;
    }

    public LocalDate getDataNascimentoMaxima() {
        return dataNascimentoMaxima;
    }

    public void setDataNascimentoMaxima(LocalDate dataNascimentoMaxima) {
        this.dataNascimentoMaxima = dataNascimentoMaxima;
    }

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        this.status = status;
    }

}
