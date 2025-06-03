package com.sistema.sistema_contabil.service;

// AcessoService: responsável por criar e consultar acessos/roles.

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sistema.sistema_contabil.model.Acesso;
import com.sistema.sistema_contabil.repository.AcessoRepository;


@Service
public class AcessoService {

    private final AcessoRepository acessoRepository;

    public AcessoService(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    // Cadastra um novo tipo de acesso (ROLE_ADMIN, ROLE_USER, etc)
    public Acesso criar(String descricao) {
        if (acessoRepository.findByDescricao(descricao).isPresent()) {
            throw new RuntimeException("Acesso já existe: " + descricao);
        }

        Acesso acesso = new Acesso();
        acesso.setDescricao(descricao);

        return acessoRepository.save(acesso);
    }

    // Busca todos os acessos
    public List<Acesso> listarTodos() {
        return acessoRepository.findAll();
    }

    // Busca por descrição
    public Optional<Acesso> buscarPorDescricao(String descricao) {
        return acessoRepository.findByDescricao(descricao);
    }
}
