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
        // --- INÍCIO: LINHAS DE DEPURAÇÃO ---
        System.out.println("DEBUG: UsuarioService - Buscando usuário no repositório por username: " + username);
        // --- FIM: LINHAS DE DEPURAÇÃO ---

        Usuario usuario = usuarioRepository.findByUsername(username);

        // --- INÍCIO: LINHAS DE DEPURAÇÃO ---
        if (usuario == null) {
            System.out.println("DEBUG: UsuarioService - findByUsername('" + username + "') retornou NULL.");
        } else {
            System.out.println("DEBUG: UsuarioService - findByUsername('" + username + "') retornou usuário: " + usuario.getUsername());
        }
        // --- FIM: LINHAS DE DEPURAÇÃO ---

        return usuario;
    }

    public void salvar(Usuario usuario) {
        // --- INÍCIO: LINHAS DE DEPURAÇÃO ---
        System.out.println("DEBUG: UsuarioService - Salvando usuário: " + usuario.getUsername());
        // --- FIM: LINHAS DE DEPURAÇÃO ---

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // --- INÍCIO: LINHAS DE DEPURAÇÃO ---
        System.out.println("DEBUG: UsuarioService - Senha codificada antes de salvar: " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        // --- FIM: LINHAS DE DEPURAÇÃO ---

        usuarioRepository.save(usuario);
    }
}
