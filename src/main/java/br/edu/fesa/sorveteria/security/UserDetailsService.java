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
        System.out.println("DEBUG: UserDetailsService - Tentando carregar usuário: " + username);

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario == null) {
            System.out.println("DEBUG: UserDetailsService - Usuário '" + username + "' NÃO ENCONTRADO no banco de dados.");
            throw new UsernameNotFoundException("Usuário não encontrado");
        } else {
            System.out.println("DEBUG: UserDetailsService - Usuário '" + username + "' ENCONTRADO. Detalhes: ID=" + usuario.getId() + ", Nome=" + usuario.getNome() + ", Email=" + usuario.getEmail());
            // CUIDADO: Não imprima a senha diretamente em logs de produção! Apenas para depuração.
            System.out.println("DEBUG: UserDetailsService - Senha do usuário (codificada) recuperada: " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        }

        // --- INÍCIO: NOVAS LINHAS DE DEPURAÇÃO ---
        // Estas linhas não afetam a lógica, mas ajudam a visualizar o que o Spring Security está recebendo.
        System.out.println("DEBUG: UserDetailsService - Construindo UserDetails para Spring Security:");
        System.out.println("DEBUG: UserDetails - Username: " + usuario.getUsername());
        System.out.println("DEBUG: UserDetails - Password (codificada): " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        System.out.println("DEBUG: UserDetails - Authorities: " + Collections.emptyList());
        // --- FIM: NOVAS LINHAS DE DEPURAÇÃO ---

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha()) // a senha deve estar codificada (ex: BCrypt)
                .authorities(Collections.emptyList()) // ou roles se tiver
                .build();
    }
}
