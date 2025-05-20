package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Cliente;
import br.edu.fesa.sorveteria.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "listaClientes";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formularioCliente";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/Cliente";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cliente", cliente);
        return "formularioCliente";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/Cliente";
    }
}
