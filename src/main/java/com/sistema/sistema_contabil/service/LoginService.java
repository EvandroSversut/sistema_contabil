package com.sistema.sistema_contabil.service;

import com.sistema.sistema_contabil.dto.LoginDTO;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(LoginDTO loginDTO) {
        return usuarioRepository.findByLoginAndSenha(loginDTO.getLogin(), loginDTO.getSenha());
    }
}
