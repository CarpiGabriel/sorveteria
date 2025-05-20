package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Sorvete;
import br.edu.fesa.sorveteria.repository.SorveteRepository;
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

    @GetMapping("/")
    public String home() {
        return "index"; // Renderiza index.html da pasta templates
    }

    @GetMapping("/lista")
    public String lista() {
        return "lista"; // Renderiza lista.html da pasta templates
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "formulario"; // Renderiza formulario.html da pasta templates
    }

    @GetMapping
    public String listarSorvetes(Model model) {
        model.addAttribute("sorvetes", sorveteRepository.findAll());
        return "lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoSorvete(Model model) {
        model.addAttribute("sorvete", new Sorvete());
        return "formulario";
    }

    @PostMapping
    public String salvarSorvete(@ModelAttribute Sorvete sorvete) {
        sorveteRepository.save(sorvete);
        return "redirect:/Sorvete";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarSorvete(@PathVariable Long id, Model model) {
        Sorvete sorvete = sorveteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("sorvete", sorvete);
        return "formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirSorvete(@PathVariable Long id) {
        sorveteRepository.deleteById(id);
        return "redirect:/Sorvete";
    }
}
