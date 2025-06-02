package com.sistema.sistema_contabil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.sistema_contabil.dto.PessoaUsuarioDTO;
import com.sistema.sistema_contabil.model.Pessoa;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;
import com.sistema.sistema_contabil.repository.PessoaRepository;
import com.sistema.sistema_contabil.repository.UsuarioRepository;


@Service
public class CadastroService {

    @Autowired
    private PessoaRepository pessoaRepo;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastrarPessoaUsuario(PessoaUsuarioDTO dto) {
        // 🔹 Cadastrar Pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setRua(dto.getRua());
        pessoa.setNumero(dto.getNumero());
        pessoa.setBairro(dto.getBairro());
        pessoa.setCep(dto.getCep());
        pessoa.setCidade(dto.getCidade());
        pessoa.setUf(dto.getUf());

        pessoa = pessoaRepo.save(pessoa);

        // 🔹 Cadastrar Pessoa Física
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf(dto.getCpf());
        pessoaFisica.setRg(dto.getRg());
      
        pessoaFisica = pessoaFisicaRepo.save(pessoaFisica);

        // 🔹 Cadastrar Usuário
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPessoaFisica(pessoaFisica);

        usuarioRepo.save(usuario);
    }
}