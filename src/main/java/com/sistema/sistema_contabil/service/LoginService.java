package com.sistema.sistema_contabil.service;

import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean autenticarUsuario(String login, String senhaDigitada) {
        System.out.println("🔍 Buscando usuário no banco com login: '" + login + "'");
        long inicio = System.nanoTime();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);
         long fim = System.nanoTime();
        System.out.println("⏱️ Tempo da consulta no banco: " + ((fim - inicio) / 1_000_000) + " ms");
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
             System.out.println("✅ Usuário encontrado: " + usuario.getLogin());
             boolean senhaOk = passwordEncoder.matches(senhaDigitada, usuario.getSenha());
        System.out.println("🔐 Senha correta? " + senhaOk);

        return senhaOk;
        }
         System.out.println("❌ Usuário não encontrado");
        return false;
    }

    public String cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByLogin(usuario.getLogin());
        if (existente.isPresent()) {
            return "Login já existe!";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso!";
    }
}
