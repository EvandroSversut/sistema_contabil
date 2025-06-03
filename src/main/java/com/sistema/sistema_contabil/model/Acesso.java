package com.sistema.sistema_contabil.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "acesso")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acesso_seq")
    @SequenceGenerator(name = "acesso_seq", sequenceName = "acesso_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String descricao; // Ex: ROLE_ADMIN, ROLE_USER

    @ManyToMany(mappedBy = "acessos")
    private List<Usuario> usuarios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

   
    
    
}
