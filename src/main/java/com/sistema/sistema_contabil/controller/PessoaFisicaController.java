package com.sistema.sistema_contabil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.service.PessoaFisicaService;

@RestController
@RequestMapping("/api/pessoas-fisicas")
public class PessoaFisicaController {

    private final PessoaFisicaService pessoaFisicaService;

    public PessoaFisicaController(PessoaFisicaService pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    // DTO de entrada
    public static class PessoaFisicaRequest {
        public String nome;
        public String telefone;
        public String rua;
        public String numero;
        public String complemento;
        public String bairro;
        public String cep;
        public String cidade;
        public String uf;
        public String cpf;
        public String rg;
    }

    @PostMapping
    public ResponseEntity<PessoaFisica> cadastrar(@RequestBody PessoaFisicaRequest request) {
        PessoaFisica pf = new PessoaFisica();
        pf.setNome(request.nome);
        pf.setTelefone(request.telefone);
        pf.setRua(request.rua);
        pf.setNumero(request.numero);
        pf.setComplemento(request.complemento);
        pf.setBairro(request.bairro);
        pf.setCep(request.cep);
        pf.setCidade(request.cidade);
        pf.setUf(request.uf);
        pf.setCpf(request.cpf);
        pf.setRg(request.rg);

        PessoaFisica salva = pessoaFisicaService.cadastrar(pf);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PessoaFisica> buscarPorCpf(@RequestParam String cpf) {
        return pessoaFisicaService.buscarPorCpf(cpf)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
