package com.sistema.sistema_contabil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.repository.PessoaJuridicaRepository;
import com.sistema.sistema_contabil.service.CadastroService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @Autowired
    private PessoaJuridicaRepository pessoaRepo;

@PostMapping("/criar-usuario")
public ResponseEntity<?> cadastrar(@RequestBody PessoaUsuarioDTO dto) {
    try {
        // chama um service que cria Pessoa, PessoaFisica e Usuario
        System.out.println("Recebido: " + dto.getNome()); // verifique se chegou
        cadastroService.cadastrarPessoaUsuario(dto);
        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @GetMapping("/listar")
    public List<PessoaFisica> listar() {
        List<PessoaFisica> lista = pessoaRepo.findAll();
        System.out.println("📜************** Lista de pessoas físicas:***************");
        //lista.forEach(p -> System.out.println(p));
        lista.forEach(p -> System.out.println("🔸 Nome: " + p.getNome() + 
                                              " | CPF: "  + p.getCpf() + 
                                              " | RG: "   + p.getRg() + 
                                              " | Telefone: " + p.getTelefone() + 
                                              " | Rua: "  + p.getRua() + 
                                              " | Numero: " + p.getNumero() + 
                                              " | Complem.: " + p.getComplemento() + 
                                              " | Bairro: " + p.getBairro() + 
                                              " | CEP: " + p.getCep() + 
                                              " | Cidade: " + p.getCidade() +
                                              " | UF: " + p.getUf() +
                                              " | Email: " + p.getEmail()
                                              ));
         System.out.println("***********************Fim da Lista********************");                                        
        return lista;
    }
    
}
