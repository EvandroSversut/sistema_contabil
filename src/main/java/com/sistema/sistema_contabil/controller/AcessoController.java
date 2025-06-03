package com.sistema.sistema_contabil.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.model.Acesso;
import com.sistema.sistema_contabil.service.AcessoService;

@RestController
@RequestMapping("/api/acessos")
public class AcessoController {

    private final AcessoService acessoService;

    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    // DTO para entrada
    public static class AcessoRequest {
        public String descricao;
    }

    @PostMapping
    public ResponseEntity<Acesso> criar(@RequestBody AcessoRequest request) {
        Acesso novo = acessoService.criar(request.descricao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Acesso>> listarTodos() {
        return ResponseEntity.ok(acessoService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Acesso> buscarPorDescricao(@RequestParam String descricao) {
        return acessoService.buscarPorDescricao(descricao)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
