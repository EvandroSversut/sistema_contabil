
package com.sistema.sistema_contabil.repository;

import com.sistema.sistema_contabil.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
}
*/

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   

       // Busca por e-mail
      Optional<Usuario> findByEmail(String email);

      // Filtra apenas usuários ativos
      List<Usuario> findByAtivoTrue();
        
      boolean existsByEmail(String email);

      // ✅ Busca pelo nome da PessoaFisica OU pelo email
      List<Usuario> findByPessoaFisicaNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(String nome, String email);
}