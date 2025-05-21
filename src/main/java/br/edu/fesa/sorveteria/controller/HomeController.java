package br.edu.fesa.sorveteria.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loginRedirect(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "index"; // Usuário autenticado -> mostra index.html
        } else {
            model.addAttribute("mensagemErro", "Você precisa estar logado para acessar a página inicial.");
            return "loga"; // Mostra login com a mensagem
        }
    }
}
