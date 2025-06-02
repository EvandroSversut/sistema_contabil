package com.sistema.sistema_contabil.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;
import com.sistema.sistema_contabil.repository.UsuarioRepository;
import com.sistema.sistema_contabil.service.EmailService;

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

    @Autowired
    private EmailService emailService;


    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarPessoaComUsuario(@RequestBody PessoaUsuarioDTO dto) {
       try{
        System.out.println("📥---------- Dados recebidos do front-end:---------");
        System.out.println("Login: " + dto.getEmail());
        System.out.println("Senha: " + dto.getSenha());
        System.out.println("Nome: " + dto.getNome());
        System.out.println("CPF: " + dto.getCpf());
        System.out.println("Telefone: " + dto.getTelefone());
        
        // Verifica se já existe login
       // if (usuarioRepo.findByEmail(dto.email).isPresent()) {
       //     return ResponseEntity.badRequest().body("Login já existe!"); // login é o email
       // }

       // Verificações de duplicidade ANTES de salvar
       if(pessoaRepo.existsByCpf(dto.getCpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cpf já cadastrado.");
       }
       if (usuarioRepo.existsByEmail(dto.getEmail())){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado.");
       }

       

         // Cria pessoa
        PessoaFisica pessoa = new PessoaFisica();
        pessoa.setNome(dto.getNome());
        pessoa.setCpf(dto.getCpf());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setEmail(dto.getEmail());

        pessoa = pessoaRepo.save(pessoa);
       
        // Cria usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPessoaFisica(pessoa);
        usuario.setDataExpiracaoAcesso(LocalDate.now().plusDays(15)); // 15 dias grátis

        usuarioRepo.save(usuario);

       // Envia e-mail de boas-vindas
    String assunto = "Bem-vindo ao Sistema Contábil";
    String mensagem = "Olá " + dto.getNome() + ",\n\n"
    + "Seu cadastro foi realizado com sucesso.\n"
    + "Você tem 15 dias gratuitos de acesso.\n\n"
    + "Login: " + dto.getEmail() + "\n"
    + "Senha: " + dto.getSenha() + "\n\n"
    + "Acesse nosso sistema e aproveite!\n\n"
    + "Atenciosamente,\nEquipe do Sistema Contábil";

    emailService.enviarEmailSimples(dto.getEmail(), assunto, mensagem);



        return ResponseEntity.ok("Cadastro realizado com sucesso!");

        } catch (DataIntegrityViolationException ex) {
        if (ex.getCause() != null && ex.getCause().getMessage().contains("cpf")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado.");
        } else if (ex.getCause() != null && ex.getCause().getMessage().contains("email")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro de integridade no banco de dados.");
        }
    } catch (Exception ex) {
    ex.printStackTrace(); // 👉 Isso exibe o erro completo no console
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("Erro interno: " + ex.getMessage());
}

 }

@GetMapping("/teste-email")
public String enviarEmailTeste() {
    emailService.enviarEmailSimples(
        "seuemaildestino@seudominio.com", 
        "Teste de Email", 
        "Esse é um e-mail de teste enviado do Spring Boot via HostGator SMTP."
    );
    return "Tentativa de envio realizada.";
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

 @GetMapping("/buscar")
 public List<PessoaFisica> buscar(@RequestParam String filtro) {
 return pessoaRepo.findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(filtro, filtro);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PessoaFisica pessoa) {
    Optional<PessoaFisica> existente = pessoaRepo.findById(id);
    if (existente.isPresent()) {
        pessoa.setId(id);
        pessoaRepo.save(pessoa);
        return ResponseEntity.ok("Pessoa atualizada com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!");
    }
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