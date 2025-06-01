package br.edu.fesa.sorveteria.controller;
import br.edu.fesa.sorveteria.service.UsuarioService;
import br.edu.fesa.sorveteria.model.Usuario;
import jakarta.servlet.http.HttpSession; // Manter se usar o logout, mas não para login/segurança
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/loga")
    public String login(Model model, @RequestParam(value = "erro", required = false) String erro) {
        if (erro != null) {
            model.addAttribute("mensagemErro", "Usuário ou senha inválidos!");
        }
        return "loga";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // O Spring Security lida com a invalidação da sessão, mas manter esta aqui não causa mal.
        session.invalidate();
        return "redirect:/loga";
    }

    // Exibir o formulário de cadastro
    @GetMapping("/register")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    // Processar o cadastro (POST)
    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes, Model model) {
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            return "redirect:/loga";
        } catch (IllegalArgumentException e) {
            model.addAttribute("usuario", usuario); // Mantém os dados preenchidos no formulário
            model.addAttribute("mensagemErro", e.getMessage()); // Exibe a mensagem de erro
            return "register"; // Volta para o formulário de registro
        }
    }
}
