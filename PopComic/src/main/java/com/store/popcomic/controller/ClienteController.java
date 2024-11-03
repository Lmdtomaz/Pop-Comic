package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/ecommerce")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    private ClienteRepository clienteService;

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
        clienteRepository.save(cliente);
        return "redirect:/"; // Redireciona para a página inicial após salvar
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

    // Formulário para editar cliente
    @GetMapping("/clientes/editar/{cpf}")
    public ModelAndView editarClienteForm(@PathVariable String cpf) {
        ModelAndView modelAndView = new ModelAndView("editarCliente");
        Optional<Cliente> cliente = clienteRepository.findById(cpf);
        cliente.ifPresent(value -> modelAndView.addObject("cliente", value));
        return modelAndView;
    }

    // Atualizar cliente
    @PostMapping("/clientes/atualizar/{cpf}")
    public String atualizarCliente(@PathVariable String cpf, @ModelAttribute Cliente clienteAtualizado) {
        clienteAtualizado.setCpf(cpf); // Mantém o CPF do cliente
        clienteRepository.save(clienteAtualizado);
        return "redirect:/ecommerce/clientes";
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
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF deve ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Validação de dígitos repetidos (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Lógica de validação do CPF
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