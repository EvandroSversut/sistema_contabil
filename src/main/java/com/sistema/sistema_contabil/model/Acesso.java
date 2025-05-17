package com.sistema.sistema_contabil.model;

import jakarta.persistence.*;

@Entity
@Table(name = "acesso")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acesso_seq")
    @SequenceGenerator(name = "acesso_seq", sequenceName = "acesso_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Ex: ROLE_ADMIN, ROLE_USER

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getters e setters
    
}
