
package com.sistema.sistema_contabil.repository;

import com.sistema.sistema_contabil.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
}
*/

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   
       Optional<Usuario> findByEmail(String email); // seu login/email
        boolean existsByEmail(String email);

        // ✅ Busca pelo nome da PessoaFisica OU pelo email
       List<Usuario> findByPessoaFisicaNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(String nome, String email);
}