package br.edu.fesa.sorveteria.controller;

import br.edu.fesa.sorveteria.model.Cliente;
import br.edu.fesa.sorveteria.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente_lista";
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente_formulario";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }
}
