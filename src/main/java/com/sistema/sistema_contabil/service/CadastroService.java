package com.sistema.sistema_contabil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;
import com.sistema.sistema_contabil.repository.PessoaJuridicaRepository;
import com.sistema.sistema_contabil.repository.UsuarioRepository;


@Service
public class CadastroService {

    @Autowired
    private PessoaJuridicaRepository pessoaRepo;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastrarPessoaUsuario(PessoaUsuarioDTO dto) {

        // 🔹 Cadastrar Pessoa Física (já como PessoaFisica)
PessoaFisica pessoaFisica = new PessoaFisica();
pessoaFisica.setNome(dto.getNome());
pessoaFisica.setEmail(dto.getEmail());
pessoaFisica.setTelefone(dto.getTelefone());
pessoaFisica.setRua(dto.getRua());
pessoaFisica.setNumero(dto.getNumero());
pessoaFisica.setComplemento(dto.getComplemento());
pessoaFisica.setBairro(dto.getBairro());
pessoaFisica.setCep(dto.getCep());
pessoaFisica.setCidade(dto.getCidade());
pessoaFisica.setUf(dto.getUf());

pessoaFisica.setCpf(dto.getCpf());
pessoaFisica.setRg(dto.getRg());

pessoaFisica = pessoaFisicaRepo.save(pessoaFisica);
System.out.println("Salvo PessoaFisica: " + pessoaFisica.getId());

 // 🔹 Cadastrar Usuário
Usuario usuario = new Usuario();
usuario.setEmail(dto.getEmail()); // pode ser igual ao da pessoa física
//usuario.setSenha(dto.getSenha());
usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
usuario.setPessoaFisica(pessoaFisica); // associa corretamente

System.out.println("Email: " + usuario.getEmail());
System.out.println("Senha: " + usuario.getSenha());
System.out.println("Usuário com ID a salvar: " + pessoaFisica);

usuario = usuarioRepo.save(usuario);

System.out.println("Usuário salvo com ID: " + usuario.getId());
/*



        // 🔹 Cadastrar Pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setRua(dto.getRua());
        pessoa.setNumero(dto.getNumero());
        pessoa.setBairro(dto.getBairro());
        pessoa.setComplemento(dto.getComplemento());
        pessoa.setCep(dto.getCep());
        pessoa.setCidade(dto.getCidade());
        pessoa.setUf(dto.getUf());

        pessoa = pessoaRepo.save(pessoa);

        // 🔹 Cadastrar Pessoa Física
        PessoaFisica pessoaFisica = new PessoaFisica();
        System.out.println("Cpf: " + dto.getCpf());
        System.out.println("RG: " + dto.getRg());
               
        pessoaFisica.setCpf(dto.getCpf());
        pessoaFisica.setRg(dto.getRg());
      
        pessoaFisica = pessoaFisicaRepo.save(pessoaFisica);

        // 🔹 Cadastrar Usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPessoaFisica(pessoaFisica);

        usuarioRepo.save(usuario);
    }
    */
}
}