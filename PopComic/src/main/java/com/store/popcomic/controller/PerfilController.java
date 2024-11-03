package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private HttpSession session;

    @GetMapping
    public ModelAndView exibirPerfil() {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login"); // Redireciona para login se não estiver logado
        }

        ModelAndView mv = new ModelAndView("perfil");
        // Adicione aqui os dados do usuário para exibir no perfil
        mv.addObject("usuario", session.getAttribute("usuarioLogado"));
        return mv;
    }
}
