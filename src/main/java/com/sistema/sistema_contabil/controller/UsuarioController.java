package com.sistema.sistema_contabil.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.service.UsuarioService;


// UsuarioController - expõe os endpoints da API REST para manipular usuários, usando os serviços que já criamos.

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // DTO de entrada para cadastro de usuário
    public static class UsuarioRequest {
        public String email;
        public String senha;
        public String cpfPessoaFisica;
        public List<String> acessos;
    }

    // POST: Cadastra um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.criarUsuario(
            request.email,
            request.senha,
            request.cpfPessoaFisica,
            request.acessos
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    // GET: Lista todos os usuários ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> listarAtivos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosAtivos());
    }

    // GET: Busca usuário por e-mail
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Desativa um usuário por ID
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioService.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
