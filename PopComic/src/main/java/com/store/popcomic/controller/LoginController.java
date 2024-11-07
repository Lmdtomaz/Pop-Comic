package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ModelAndView novoClienteForm() {
        ModelAndView mv = new ModelAndView("login"); // "login" deve corresponder ao nome do arquivo login.html
        mv.addObject("usuario", new Cliente());
        return mv;
    }

    @PostMapping
    public ModelAndView login(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Optional<Cliente> cliente = clienteRepository.findByEmailAndSenha(email, senha);
        if (cliente.isPresent()) {
            session.setAttribute("usuarioLogado", cliente.get());
            System.out.println("Cliente Logado");
            return new ModelAndView("redirect:/"); // Aqui é onde você redireciona após o login.
        } else {
            ModelAndView mv = new ModelAndView("login");
            System.out.println("Cliente NAO Logado");
            mv.addObject("erro", "Email ou senha inválidos");
            return mv;
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "redirect:/login"; // Redireciona para a página de login após logout
    }
    // Método para criar uma nova sessão
    @GetMapping("/createSession")
    public String createSession(HttpSession session) {
        // Define um atributo de sessão (exemplo: nome de usuário)
        session.setAttribute("usuarioLogado", "NomeDoUsuario");
        return "Sessão criada com ID: " + session.getId();
    }

    // Método para obter um atributo da sessão
    @GetMapping("/getSession")
    public String getSession(HttpSession session) {
        // Obtém o atributo da sessão (nome de usuário)
        String usuarioLogado = (String) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "Nenhuma sessão encontrada!";
        }

        return "Sessão encontrada com usuário: " + usuarioLogado;
    }

    // Método para invalidar a sessão
    @GetMapping("/invalidateSession")
    public String invalidateSession(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "Sessão invalidada!";
    }
}
