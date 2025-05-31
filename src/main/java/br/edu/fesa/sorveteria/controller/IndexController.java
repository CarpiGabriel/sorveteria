package br.edu.fesa.sorveteria.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirecionarParaIndex() {
        return "/index";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

}
