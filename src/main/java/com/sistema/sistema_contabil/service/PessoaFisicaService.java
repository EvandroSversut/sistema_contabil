package com.sistema.sistema_contabil.service;

// PessoaFisicaService: responsável por cadastrar e consultar pessoas físicas.

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;


@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    // Cadastra nova pessoa física
    public PessoaFisica cadastrar(PessoaFisica pessoaFisica) {
        if (pessoaFisicaRepository.findByCpf(pessoaFisica.getCpf()).isPresent()) {
            throw new RuntimeException("Pessoa física já cadastrada com este CPF.");
        }

        return pessoaFisicaRepository.save(pessoaFisica);
    }

    // Busca por CPF
    public Optional<PessoaFisica> buscarPorCpf(String cpf) {
        return pessoaFisicaRepository.findByCpf(cpf);
    }
}
