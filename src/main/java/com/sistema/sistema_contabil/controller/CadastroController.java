package com.sistema.sistema_contabil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.service.CadastroService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

@PostMapping("/cadastro")
public ResponseEntity<?> cadastrar(@RequestBody PessoaUsuarioDTO dto) {
    try {
        // chama um service que cria Pessoa, PessoaFisica e Usuario
        cadastroService.cadastrarPessoaUsuario(dto);
        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    
}
