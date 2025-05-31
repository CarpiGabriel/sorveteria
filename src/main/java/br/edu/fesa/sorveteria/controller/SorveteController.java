package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Sorvete;
import br.edu.fesa.sorveteria.repository.SorveteRepository;
// import jakarta.servlet.http.HttpSession; // Remova esta importação se não for mais usada
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Sorvete")
public class SorveteController {

    private final SorveteRepository sorveteRepository;

    public SorveteController(SorveteRepository sorveteRepository) {
        this.sorveteRepository = sorveteRepository;
    }

    // REMOVA ESTE MÉTODO INTEIRO
    // private boolean usuarioNaoLogado(HttpSession session) {
    //     return session.getAttribute("usuarioLogado") == null;
    // }

    @GetMapping
    public String listarSorvetes(Model model /*, HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        model.addAttribute("sorvetes", sorveteRepository.findAll());
        return "lista"; // Este HTML está na pasta templates? Você tinha lista.html e listaClientes.html.
        // Se for o de sorvetes, então "lista" é o correto.
    }

    @GetMapping("/formulario")
    public String formularioNovo(Model model /*, HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        model.addAttribute("sorvete", new Sorvete());
        return "formulario";
    }

    @PostMapping
    public String salvarSorvete(@ModelAttribute Sorvete sorvete /*, HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        sorveteRepository.save(sorvete);
        return "redirect:/Sorvete";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model /*, HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        Sorvete sorvete = sorveteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("sorvete", sorvete);
        return "formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id /*, HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        sorveteRepository.deleteById(id);
        return "redirect:/Sorvete";
    }

    @GetMapping("/home")
    public String home(/* HttpSession session */) { // Remova HttpSession do parâmetro
        // REMOVA ESTA LINHA: if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        return "index";
    }
}
