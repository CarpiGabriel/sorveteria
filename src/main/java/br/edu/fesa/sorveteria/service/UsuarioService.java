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
        return usuarioRepository.findByUsername(username);
    }

    public void salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }
}
