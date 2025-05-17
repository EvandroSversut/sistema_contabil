package com.sistema.sistema_contabil.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column
    private String telefone;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Getters e setters
    
}
