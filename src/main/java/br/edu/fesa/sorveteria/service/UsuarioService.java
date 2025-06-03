package br.edu.fesa.sorveteria.service;

import br.edu.fesa.sorveteria.model.Usuario;
import br.edu.fesa.sorveteria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario buscarPorUsername(String username) {
        System.out.println("DEBUG: UsuarioService - Buscando usuário no repositório por username: " + username);
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            System.out.println("DEBUG: UsuarioService - findByUsername('" + username + "') retornou NULL.");
        } else {
            System.out.println("DEBUG: UsuarioService - findByUsername('" + username + "') retornou usuário: " + usuario.getUsername());
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void salvar(Usuario usuario) {
        System.out.println("DEBUG: UsuarioService - Salvando usuário: " + usuario.getUsername());

        Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente != null && (usuario.getId() == null || !usuarioExistente.getId().equals(usuario.getId()))) {
            throw new IllegalArgumentException("O e-mail '" + usuario.getEmail() + "' já está cadastrado.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // NOVO: Atribui uma role padrão se não for especificada
        if (usuario.getRole() == null || usuario.getRole().isEmpty()) {
            usuario.setRole("ROLE_USER"); // Role padrão para novos cadastros
        }

        System.out.println("DEBUG: UsuarioService - Senha codificada antes de salvar: " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        System.out.println("DEBUG: UsuarioService - Role atribuída: " + usuario.getRole()); // Depuração da role

        usuarioRepository.save(usuario);
    }
}
