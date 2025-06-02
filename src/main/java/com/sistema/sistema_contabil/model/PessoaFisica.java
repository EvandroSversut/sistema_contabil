package com.sistema.sistema_contabil.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String rg;

    //@Column(nullable = false)
    //private String estadoCivil;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return "PessoaFisica [cpf=" + cpf + ", rg=" + rg + super.toString() + "]";
    }

   
    
}
