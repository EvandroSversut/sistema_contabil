package com.sistema.sistema_contabil.security;

import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles("USER") // ou ADMIN se quiser expandir depois
                .build();
    }
}
