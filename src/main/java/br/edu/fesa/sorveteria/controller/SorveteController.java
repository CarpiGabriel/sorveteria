package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Sorvete;
import br.edu.fesa.sorveteria.repository.SorveteRepository;
import jakarta.servlet.http.HttpSession;
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

    private boolean usuarioNaoLogado(HttpSession session) {
        return session.getAttribute("usuarioLogado") == null;
    }

    @GetMapping
    public String listarSorvetes(Model model, HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        model.addAttribute("sorvetes", sorveteRepository.findAll());
        return "lista";
    }

    @GetMapping("/formulario")
    public String formularioNovo(Model model, HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        model.addAttribute("sorvete", new Sorvete());
        return "formulario";
    }

    @PostMapping
    public String salvarSorvete(@ModelAttribute Sorvete sorvete, HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        sorveteRepository.save(sorvete);
        return "redirect:/Sorvete";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        Sorvete sorvete = sorveteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("sorvete", sorvete);
        return "formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        sorveteRepository.deleteById(id);
        return "redirect:/Sorvete";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (usuarioNaoLogado(session)) return "redirect:/loga?erro=nao_autorizado";

        return "index";
    }
}
