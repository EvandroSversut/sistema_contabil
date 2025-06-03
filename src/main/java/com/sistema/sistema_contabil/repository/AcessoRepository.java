package com.sistema.sistema_contabil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.sistema_contabil.model.Acesso;

// Os repositórios são interfaces responsáveis por acessar o banco de dados. 
//Utilizamos o JpaRepository para aproveitar métodos prontos como save, findById, delete, findAll, etc.


// O @Repository é opcional com Spring Boot 3+, mas mantemos por clareza.
@Repository
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    // Optional<> é usado para evitar null (boa prática).
    Optional<Acesso> findByDescricao(String descricao);
}
