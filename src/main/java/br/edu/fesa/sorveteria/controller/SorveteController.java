package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Sorvete;
import br.edu.fesa.sorveteria.repository.SorveteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Importe esta classe

@Controller
@RequestMapping("/Sorvete")
public class SorveteController {

    private final SorveteRepository sorveteRepository;

    public SorveteController(SorveteRepository sorveteRepository) {
        this.sorveteRepository = sorveteRepository;
    }

    @GetMapping
    public String listarSorvetes(Model model) {
        model.addAttribute("sorvetes", sorveteRepository.findAll());
        return "lista";
    }

    @GetMapping("/formulario")
    public String formularioNovo(Model model) {
        model.addAttribute("sorvete", new Sorvete());
        return "formulario";
    }

    @PostMapping
    public String salvarSorvete(@ModelAttribute Sorvete sorvete) {
        sorveteRepository.save(sorvete);
        return "redirect:/Sorvete";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Sorvete sorvete = sorveteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("sorvete", sorvete);
        return "formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        sorveteRepository.deleteById(id);
        return "redirect:/Sorvete";
    }

    @GetMapping("/home") // Este método parece ser redundante se o IndexController já cuida da raiz
    public String home() {
        return "index";
    }

    // --- NOVO MÉTODO PARA O DASHBOARD DE SORVETES ---
    @GetMapping("/informacoes")
    public String mostrarDashboardSorvetes(Model model) {
        List<Sorvete> todosOsSorvetes = sorveteRepository.findAll();
        Double mediaPreco = sorveteRepository.findAveragePreco();

        // Formata a média para ter 2 casas decimais, ou lida com caso de não haver sorvetes
        String mediaFormatada = "N/A";
        if (mediaPreco != null) {
            mediaFormatada = String.format("%.2f", mediaPreco);
        }

        model.addAttribute("todosOsSorvetes", todosOsSorvetes);
        model.addAttribute("mediaPrecoSorvetes", mediaFormatada);
        model.addAttribute("totalSorvetes", todosOsSorvetes.size()); // Adiciona contagem

        return "sorvete_dashboard"; // Retorna o nome do novo HTML
    }
}
