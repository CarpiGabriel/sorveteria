    package br.edu.fesa.sorveteria.controller;

    import br.edu.fesa.sorveteria.model.Cliente;
    import br.edu.fesa.sorveteria.repository.ClienteRepository;
    import jakarta.servlet.http.HttpSession;
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
        public String listarClientes(Model model, HttpSession session) {
            if (session.getAttribute("usuarioLogado") == null) {
                return "redirect:/loga?erro=nao_autorizado";
            }

            // aqui seu código normal:
            model.addAttribute("clientes", clienteRepository.findAll());
            return "listaClientes"; // ou o nome do seu HTML de clientes
        }

        @GetMapping("/novo")
        public String mostrarFormularioNovoCliente(Model model) {
            model.addAttribute("cliente", new Cliente());
            return "formularioCliente";
        }

        @PostMapping
        public String salvarCliente(@ModelAttribute Cliente cliente) {
            if (cliente.getId() != null) {
                // Atualiza cliente existente
                Cliente existente = clienteRepository.findById(cliente.getId())
                        .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + cliente.getId()));

                existente.setNome(cliente.getNome());
                existente.setEmail(cliente.getEmail());
                existente.setTelefone(cliente.getTelefone());
                existente.setCep(cliente.getCep());
                existente.setRua(cliente.getRua());
                existente.setBairro(cliente.getBairro());
                existente.setNumero(cliente.getNumero());
                existente.setComplemento(cliente.getComplemento());

                clienteRepository.save(existente);
            } else {
                // Novo cliente
                clienteRepository.save(cliente);
            }

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
