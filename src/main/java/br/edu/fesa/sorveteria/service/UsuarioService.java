package br.edu.fesa.sorveteria.service;

import br.edu.fesa.sorveteria.model.Usuario;
import br.edu.fesa.sorveteria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
public Usuario buscarPorUsernameESenha(String username, String senha) {
    return usuarioRepository.findByUsernameAndSenha(username, senha);
}
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
