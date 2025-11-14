package br.edu.ifce.maracanau.controleacademico.model;

import br.edu.ifce.maracanau.controleacademico.model.enums.Cargo;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseModel {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

}
