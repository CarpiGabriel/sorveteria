package br.edu.fesa.sorveteria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set; // Importe Set
import java.util.HashSet; // Importe HashSet

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    // NOVO CAMPO: Role do usuário
    // Usaremos uma String simples para a role (ex: "ROLE_USER", "ROLE_ADMIN")
    // Ou, para múltiplos papéis, pode ser @ElementCollection ou @ManyToMany
    // Para este exemplo, vamos com uma String simples que representa a role principal.
    @Column(nullable = false)
    private String role; // Ex: "ROLE_USER", "ROLE_ADMIN"

    // Se você quiser que um usuário possa ter múltiplas roles, usaria algo assim:
    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    // @Column(name = "role")
    // private Set<String> roles = new HashSet<>();
}
