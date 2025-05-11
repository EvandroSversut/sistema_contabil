package com.sistema.sistema_contabil.controller;

import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.UsuarioRepository;
import com.sistema.sistema_contabil.service.LoginService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

      @Autowired
    private LoginService loginService;

    @PostMapping
    public boolean login(@RequestBody Usuario usuario) {
        System.out.println("Login recebido: " + usuario.getLogin());
        return loginService.autenticarUsuario(usuario.getLogin(), usuario.getSenha());
    }

    @PostMapping("/criar-usuario")
    public String criarUsuario(@RequestBody Usuario usuario) {
        return loginService.cadastrarUsuario(usuario);
    }
}




/* 

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        System.out.println("Recebido do front:");
        System.out.println("Login: " + usuario.getLogin());
        System.out.println("Senha: " + usuario.getSenha());

         Optional<Usuario> userOpt = usuarioRepository.findByLogin(usuario.getLogin());

    if (userOpt.isPresent()) {
        Usuario user = userOpt.get();

        if (passwordEncoder.matches(usuario.getSenha(), user.getSenha())) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
}

*/

        
/* 
        boolean usuarioValido = usuarioRepository
                .findByLoginAndSenha(usuario.getLogin(), usuario.getSenha())
                .isPresent();

        if (usuarioValido) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        }
    }

*/
/* 
        // endpoint temporário para criar usuários com senha criptografada
        @PostMapping("/criar-usuario")
        public String criarUsuario(@RequestBody Usuario usuario) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioRepository.save(usuario);
            return "Usuário criado com sucesso!";
}

}
*/