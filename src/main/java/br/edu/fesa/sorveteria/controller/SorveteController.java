package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Sorvete;
import br.edu.fesa.sorveteria.repository.SorveteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Sorvete")
public class SorveteController {

    private final SorveteRepository sorveteRepository;

    public SorveteController(SorveteRepository sorveteRepository) {
        this.sorveteRepository = sorveteRepository;
    }

    // Este método AGORA será sua home page padrão, exibindo os botões (o que era index.html)
    @GetMapping
    public String homeSorvetes(Model model) {
        // Você pode adicionar atributos ao modelo aqui se sua home precisar de dados
        return "lista"; // Retorna a view 'lista.html' que agora tem o conteúdo da home
    }

    // --- NOVO MÉTODO PARA A TABELA DE SORVETES ---
    @GetMapping("/lista-tabela") // Nova URL para a tabela de sorvetes
    public String listarSorvetesTabela(Model model) {
        model.addAttribute("sorvetes", sorveteRepository.findAll());
        return "lista_tabela"; // Retorna o nome do HTML da tabela (vamos criar este)
    }

    @GetMapping("/formulario")
    public String formularioNovo(Model model) {
        model.addAttribute("sorvete", new Sorvete());
        return "formulario";
    }

    @PostMapping
    public String salvarSorvete(@ModelAttribute Sorvete sorvete) {
        sorveteRepository.save(sorvete);
        return "redirect:/Sorvete/lista-tabela"; // Redireciona para a tabela após salvar
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
        return "redirect:/Sorvete/lista-tabela"; // Redireciona para a tabela após excluir
    }

    @GetMapping("/informacoes")
    public String mostrarDashboardSorvetes(Model model) {
        List<Sorvete> todosOsSorvetes = sorveteRepository.findAll();
        Double mediaPreco = sorveteRepository.findAveragePreco();

        String mediaFormatada = "N/A";
        if (mediaPreco != null) {
            mediaFormatada = String.format("%.2f", mediaPreco);
        }

        model.addAttribute("todosOsSorvetes", todosOsSorvetes);
        model.addAttribute("mediaPrecoSorvetes", mediaFormatada);
        model.addAttribute("totalSorvetes", todosOsSorvetes.size());

        return "sorvete_dashboard";
    }

    @GetMapping("/precos")
    public String mostrarPrecosSorvetes(Model model) {
        List<Sorvete> sorvetes = sorveteRepository.findAll();
        model.addAttribute("sorvetes", sorvetes);
        return "preco";
    }
}
