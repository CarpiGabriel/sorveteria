package br.edu.fesa.sorveteria.controller;

// import jakarta.servlet.http.HttpSession; // Não é mais necessário para este controller
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Este método será chamado ao acessar a raiz da aplicação
    public String home() {
        return "index"; // Retorna o nome da view "index.html"
    }

    // Remova o método loginRedirect() se ele não for mais usado, ou o deixe como comentário.
    /*@GetMapping("/")
    public String loginRedirect(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "index"; // Usuário autenticado -> mostra index.html
        } else {
            model.addAttribute("mensagemErro", "Você precisa estar logado para acessar a página inicial.");
            return "loga"; // Mostra login com a mensagem
        }
    }*/
}
