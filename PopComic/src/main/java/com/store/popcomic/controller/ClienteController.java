package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/ecommerce")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/cadastro")
    public ModelAndView novoClienteForm() {
        ModelAndView mv = new ModelAndView("cadastro");
        mv.addObject("cliente", new Cliente());
        return mv;
    }

    // Salvar novo cliente
    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente, Model model) {
        if (!isCpfValido(cliente.getCpf())) {
            model.addAttribute("erro", "CPF inválido.");
            return "cadastro"; // Retorna ao formulário de cadastro com erro
        }

        Optional<Cliente> clienteExistente = clienteRepository.findById(cliente.getCpf());
        if (clienteExistente.isPresent()) {
            model.addAttribute("erro", "Já existe um cliente cadastrado com este CPF.");
            return "cadastro"; // Retorna ao formulário de cadastro com erro
        }

        clienteRepository.save(cliente);
        return "redirect:/"; // Redireciona para a página inicial após salvar
    }
    @GetMapping("/buscarEndereco/{cep}")
    @ResponseBody
    public Map<String, String> buscarEndereco(@PathVariable String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // Verifica se o CEP é válido
            if (response != null && response.containsKey("erro") && (boolean) response.get("erro")) {
                throw new RuntimeException("CEP não encontrado.");
            }

            // Extrai os campos necessários
            Map<String, String> endereco = new HashMap<>();
            endereco.put("logradouro", (String) response.get("logradouro"));
            endereco.put("cidade", (String) response.get("localidade"));
            endereco.put("estado", (String) response.get("uf"));

            return endereco;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o endereço.", e);
        }
    }

    // Listar todos os clientes
    @GetMapping("/clientes")
    public ModelAndView listarClientes() {
        ModelAndView modelAndView = new ModelAndView("listaClientes");
        modelAndView.addObject("clientes", clienteRepository.findAll());
        return modelAndView;
    }

    // Buscar cliente por CPF
    @GetMapping("/clientes/{cpf}")
    public ModelAndView buscarClientePorCpf(@PathVariable String cpf) {
        ModelAndView modelAndView = new ModelAndView("detalhesCliente");
        Optional<Cliente> cliente = clienteRepository.findById(cpf);
        cliente.ifPresent(value -> modelAndView.addObject("cliente", value));
        return modelAndView;
    }

    @GetMapping("/usuario/editar/{cpf}")
    public ModelAndView editarPerfil(@PathVariable String cpf, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("editarCliente");
        Optional<Cliente> cliente = clienteRepository.findById(cpf);

        if (cliente.isPresent()) {
            modelAndView.addObject("cliente", cliente.get());
        } else {
            modelAndView.addObject("error", "Cliente não encontrado.");
        }

        return modelAndView;
    }

    @PostMapping("/usuario/atualizar")
    public String atualizarCliente(@ModelAttribute Cliente clienteAtualizado, HttpSession session) {
        String cpf = (String) session.getAttribute("userCpf");

        // Busca o cliente existente no banco de dados
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findById(cpf);

        if (clienteExistenteOpt.isPresent()) {
            Cliente clienteExistente = clienteExistenteOpt.get();

            // Atualiza apenas os campos que foram enviados
            if (clienteAtualizado.getNome() != null) {
                clienteExistente.setNome(clienteAtualizado.getNome());
            }
            if (clienteAtualizado.getEmail() != null) {
                clienteExistente.setEmail(clienteAtualizado.getEmail());
            }
            if (clienteAtualizado.getDataNascimento() != null) {
                clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
            }
            if (clienteAtualizado.getGenero() != null) {
                clienteExistente.setGenero(clienteAtualizado.getGenero());
            }
            // Não atualiza a senha se não for enviada
            if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
                clienteExistente.setSenha(clienteAtualizado.getSenha());
            }

            // Salva as alterações no banco de dados
            clienteRepository.save(clienteExistente);
        }

        // Redireciona para a página de perfil
        return "redirect:/perfil"; // Redireciona para a página de detalhes do cliente
    }

    // Excluir cliente
    @GetMapping("/clientes/excluir/{cpf}")
    public String excluirCliente(@PathVariable String cpf) {
        clienteRepository.deleteById(cpf);
        return "redirect:/ecommerce/clientes";
    }

    // Método para validar CPF
    private boolean isCpfValido(String cpf) {
        // Implementação básica para validação de CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 >= 10) digito1 = 0;
        if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 >= 10) digito2 = 0;
        return digito2 == Character.getNumericValue(cpf.charAt(10));
    }
}
