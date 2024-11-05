package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.ClienteRepository; // Importando o repositório
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ClienteRepository clienteRepository; // Injetando o repositório

    @GetMapping
    public ModelAndView exibirPerfil() {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login"); // Redireciona para login se não estiver logado
        }

        String cpf = (String) session.getAttribute("userCpf"); // Obtendo o CPF do usuário da sessão
        Optional<Cliente> clienteOpt = clienteRepository.findById(cpf); // Buscando o cliente no banco de dados

        ModelAndView mv = new ModelAndView("perfil");
        if (clienteOpt.isPresent()) {
            mv.addObject("usuario", clienteOpt.get()); // Adiciona o cliente encontrado ao ModelAndView
        } else {
            mv.addObject("error", "Cliente não encontrado."); // Adiciona uma mensagem de erro se não encontrado
        }

        return mv;
    }
}