package br.edu.fesa.sorveteria.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/loga?erro=nao_autorizado";
        }
        return "index";
    }
}
