package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("usuario", new Cliente()); // Cria um novo objeto Cliente para o formulário
        return "login"; // Retorna o nome da sua página de login
    }

    @PostMapping
    public String login(Cliente usuario, Model model) {
        Cliente cliente = clienteService.buscarPorCpf(usuario.getCpf()); // Busca cliente pelo CPF

        if (cliente != null && cliente.verificarSenha(usuario.getSenha())) {
            // Se o cliente existe e a senha está correta, crie a sessão
            model.addAttribute("clienteLogado", cliente); // Armazena o cliente na sessão
            return "redirect:/index.html"; // Redireciona para a página inicial após login bem-sucedido
        }

        // Se falhar, adicionar mensagem de erro
        model.addAttribute("mensagem", "CPF ou senha inválidos.");
        return "login"; // Retorna à página de login
    }
}
