package br.edu.fesa.sorveteria.security;

import br.edu.fesa.sorveteria.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private br.edu.fesa.sorveteria.service.UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // Aqui você pode adaptar a criação do User do Spring Security,
        // assumindo que sua senha já está armazenada codificada.
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha()) // a senha deve estar codificada (ex: BCrypt)
                .authorities(Collections.emptyList()) // ou roles se tiver
                .build();
    }
}
