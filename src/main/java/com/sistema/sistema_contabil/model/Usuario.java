package com.sistema.sistema_contabil.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "pessoa_fisica_id")
    private PessoaFisica pessoaFisica;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_acesso", // essa anotacao cria a tabela "usuario_acesso"
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "acesso_id")
    ) //✅ Permite que um usuário tenha múltiplos acessos e um acesso seja compartilhado entre vários usuários.
    private Set<Acesso> acessos;

    
}
