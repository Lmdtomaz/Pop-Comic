package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.Endereco;
import com.store.popcomic.repository.ClienteRepository;
import com.store.popcomic.repository.EnderecoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/perfil")
public class EnderecoController {



    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Rota para exibir o formulário de cadastro
    @GetMapping("/endereco/{cpf}")
    public String mostrarFormularioCadastro(@PathVariable String cpf, Model model) {
        // Buscar o cliente pelo CPF
        Cliente cliente = clienteRepository.findByCpf(cpf).orElse(null);
        if (cliente == null) {
            model.addAttribute("error", "Cliente com o CPF informado não encontrado!");
            return "Endereco"; // Retorna o formulário com erro
        }

        // Adiciona o CPF ao modelo para o formulário
        model.addAttribute("cpf", cpf);

        // Inicializa um novo objeto Endereco
        model.addAttribute("endereco", new Endereco());

        return "Endereco"; // Nome do template para o formulário
    }

    // Rota para salvar um novo endereço
    @PostMapping("/endereco/{cpf}")
    public String cadastrarEndereco(@PathVariable String cpf, @ModelAttribute Endereco endereco, Model model) {
        // Busca o cliente no banco de dados pelo CPF
        Cliente cliente = clienteRepository.findByCpf(cpf).orElse(null);

        if (cliente == null) {
            model.addAttribute("error", "Cliente com o CPF informado não encontrado!");
            return "Endereco"; // Retorna ao formulário em caso de erro
        }

        // Associa o cliente ao endereço
        endereco.setCliente(cliente);

        // Salva o endereço no banco de dados
        enderecoRepository.save(endereco);

        // Redireciona para a página do perfil após o cadastro
        return "redirect:/perfil"; // Redireciona para o perfil do cliente
    }

}