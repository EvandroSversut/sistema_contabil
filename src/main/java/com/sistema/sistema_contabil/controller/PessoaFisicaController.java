package com.sistema.sistema_contabil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;

@RestController
@RequestMapping("/api/pessoa-fisica")
@CrossOrigin(origins = "http://localhost:4200")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository repository;

   @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PessoaFisica pessoa) {
    try {
        PessoaFisica salva = repository.save(pessoa);
        return ResponseEntity.ok(salva);
    } catch (DataIntegrityViolationException ex) {
        // campo unique violado
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("E-mail já cadastrado!");
    }
}


    @GetMapping
    public List<PessoaFisica> listar() {
        return repository.findAll();
    }
}
