package br.edu.fesa.sorveteria.security;

import br.edu.fesa.sorveteria.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.ArrayList; // Importe ArrayList
import java.util.List;     // Importe List
import org.springframework.security.core.GrantedAuthority; // Importe GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importe SimpleGrantedAuthority


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
            System.out.println("DEBUG: UserDetailsService - Usuário '" + username + "' ENCONTRADO. Detalhes: ID=" + usuario.getId() + ", Nome=" + usuario.getNome() + ", Email=" + usuario.getEmail() + ", Role=" + usuario.getRole());
            System.out.println("DEBUG: UserDetailsService - Senha do usuário (codificada) recuperada: " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        }

        // --- ATUALIZADO: Retorna as roles do usuário ---
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Adiciona a role do usuário, prefixando com "ROLE_"
        authorities.add(new SimpleGrantedAuthority(usuario.getRole()));

        System.out.println("DEBUG: UserDetailsService - Construindo UserDetails para Spring Security:");
        System.out.println("DEBUG: UserDetails - Username: " + usuario.getUsername());
        System.out.println("DEBUG: UserDetails - Password (codificada): " + usuario.getSenha().substring(0, Math.min(usuario.getSenha().length(), 15)) + "...");
        System.out.println("DEBUG: UserDetails - Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getSenha(),
                authorities // Agora passa as roles reais
        );
    }
}