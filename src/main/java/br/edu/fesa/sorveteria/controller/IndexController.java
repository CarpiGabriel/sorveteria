package br.edu.fesa.sorveteria.controller;

// import jakarta.servlet.http.HttpSession; // Remova esta importação se não for mais usada
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // REMOVA ESTE MÉTODO INTEIRO QUE CAUSA O CONFLITO:
    /*
    @GetMapping("/")
    public String redirecionarParaIndex() {
        return "/index";
    }
    */

    @GetMapping("/index") // Mantenha este se você quiser que /index também mostre o dashboard
    public String indexPage() {
        return "index";
    }

}
