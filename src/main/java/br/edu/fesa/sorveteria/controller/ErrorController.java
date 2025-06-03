package br.edu.fesa.sorveteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso_negado"; // Nome do novo HTML
    }
}
