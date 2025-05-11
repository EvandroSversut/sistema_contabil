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
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return passwordEncoder.matches(senhaDigitada, usuario.getSenha());
        }
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
