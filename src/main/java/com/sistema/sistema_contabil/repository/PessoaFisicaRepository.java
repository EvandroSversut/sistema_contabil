package com.sistema.sistema_contabil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.sistema_contabil.model.PessoaFisica;


@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

boolean existsByCpf(String cpf);

List<PessoaFisica> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(String nome, String email);
    
}
