package com.sistema.sistema_contabil.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;
import com.sistema.sistema_contabil.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/pessoa-fisica")
@CrossOrigin(origins = "http://localhost:4200")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

     @Autowired
    private PasswordEncoder passwordEncoder;


      @PostMapping("/completo")
    public ResponseEntity<String> cadastrarPessoaComUsuario(@RequestBody PessoaUsuarioDTO dto) {
        System.out.println("📥 Dados recebidos do front-end:");
        System.out.println("Nome: " + dto.getNome());
        System.out.println("Email: " + dto.getEmail());
        System.out.println("CPF: " + dto.getCpf());
        System.out.println("Telefone: " + dto.getTelefone());
        System.out.println("Login: " + dto.getEmail());
        System.out.println("Senha: " + dto.getSenha());

        // Verifica se já existe login
        if (usuarioRepo.findByEmail(dto.email).isPresent()) {
            return ResponseEntity.badRequest().body("Login já existe!"); // login é o email
        }

        PessoaFisica pessoa = new PessoaFisica();
        pessoa.setNome(dto.nome);
        pessoa.setEmail(dto.email);
        pessoa.setCpf(dto.cpf);
        pessoa.setTelefone(dto.telefone);
        pessoa = pessoaRepo.save(pessoa);

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email);
        usuario.setSenha(passwordEncoder.encode(dto.senha));
        usuario.setPessoaFisica(pessoa);
        usuario.setDataExpiracaoAcesso(LocalDate.now().plusDays(15)); // 15 dias grátis
        usuarioRepo.save(usuario);

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }
}


/*

   @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PessoaFisica pessoa) {
    try {
        PessoaFisica salva = repository.save(pessoa);
        return ResponseEntity.ok(salva);
    } catch (DataIntegrityViolationException ex) {
        // campo unique violado
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Pessoa já Cadastrada!!");
    }
}


    @GetMapping
    public List<PessoaFisica> listar() {
        return repository.findAll();
    }
}

 */