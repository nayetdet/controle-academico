package br.edu.ifce.maracanau.controleacademico.model;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseModel {

    protected BaseModel() {}

    protected BaseModel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    protected BaseModel(Long id, Usuario responsavel) {
        this.id = id;
        this.responsavel = responsavel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseModel baseModel)) return false;
        return Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

}
