package com.sistema.sistema_contabil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.sistema_contabil.model.Acesso;
import com.sistema.sistema_contabil.model.PessoaFisica;
import com.sistema.sistema_contabil.model.Usuario;
import com.sistema.sistema_contabil.repository.AcessoRepository;
import com.sistema.sistema_contabil.repository.PessoaFisicaRepository;
import com.sistema.sistema_contabil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

// A camada de serviço é onde colocamos a regra de negócio. 

// Não acessamos o banco diretamente no controller — usamos os serviços para isso.

/*
🧠 O que essa classe vai fazer:
Criar um novo usuário vinculado à pessoa física

Associar um ou mais acessos

Verificar se o usuário está ativo

Buscar usuário por email

Buscar todos os usuários ativos
 */

 
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final AcessoRepository acessoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PessoaFisicaRepository pessoaFisicaRepository,
        AcessoRepository acessoRepository,
        BCryptPasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.acessoRepository = acessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuario(String email, String senha, String cpf, List<String> acessosDescricao) {
        PessoaFisica pessoa = pessoaFisicaRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException("Pessoa física não encontrada com o CPF informado"));

        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        // 🔒 Senha criptografada com BCrypt
        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(senhaCriptografada);

        usuario.setPessoaFisica(pessoa);
        usuario.setAtivo(true);

        List<Acesso> acessos = new ArrayList<>();
        for (String desc : acessosDescricao) {
            Acesso acesso = acessoRepository.findByDescricao(desc)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado: " + desc));
            acessos.add(acesso);
        }

        usuario.setAcessos(acessos);

        return usuarioRepository.save(usuario);
    }
    
    // Outros métodos como buscarPorEmail(), listarUsuariosAtivos(), etc.

    public List<Usuario> listarUsuariosAtivos() {
    return usuarioRepository.findAll()
            .stream()
            .filter(Usuario::isAtivo)
            .collect(Collectors.toList());

        // Você pode trocar esse filtro por uma consulta direta ao banco, se preferir mais performance:
            //  @Query("SELECT u FROM Usuario u WHERE u.ativo = true")
            //  List<Usuario> findUsuariosAtivos();
            
        }

    public Optional<Usuario> buscarPorEmail(String email) {
    return usuarioRepository.findByEmail(email);
}

    @Transactional
    public void desativarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuario.setAtivo(false);
    usuarioRepository.save(usuario);
}


}
