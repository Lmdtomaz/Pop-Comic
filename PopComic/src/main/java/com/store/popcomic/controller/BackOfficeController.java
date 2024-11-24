package com.store.popcomic.controller;

import com.store.popcomic.model.Usuario;
import com.store.popcomic.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class BackOfficeController {


    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("areaadm")
    public ModelAndView backOffice(HttpSession session) {
        ModelAndView mv = new ModelAndView();

        // Verifica se há um usuário logado na sessão
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            // Usuário logado, exibe a página de backoffice
            mv.setViewName("backoffice");
            mv.addObject("usuario", usuarioLogado);
        } else {
            // Redireciona para a tela de login caso não haja usuário logado
            mv.setViewName("redirect:/adm/usuario/loginAdm");
        }

        return mv;
    }

}
