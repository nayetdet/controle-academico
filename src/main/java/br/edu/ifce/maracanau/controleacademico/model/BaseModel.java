package br.edu.ifce.maracanau.controleacademico.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
