package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Usuario;
import br.edu.fesa.sorveteria.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/loga")
    public String processaLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session) {
        Usuario usuario = usuarioService.buscarPorUsernameESenha(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/index";
        } else {
            return "redirect:/loga?erro=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
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
    public String register(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        usuarioService.salvar(usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
        return "redirect:/loga";
    }
}
