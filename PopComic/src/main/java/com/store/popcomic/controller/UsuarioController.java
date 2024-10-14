package com.store.popcomic.controller;


import com.store.popcomic.model.UsuarioCliente;
import com.store.popcomic.repository.UsuarioDao;
import com.store.popcomic.service.ServiceExc;
import com.store.popcomic.service.ServiceUsuario;
import com.store.popcomic.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

@Controller
public class UsuarioController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioDao usuarioRepositorio;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        mv.setViewName("login");
        mv.addObject("usuario", new UsuarioCliente());
        return mv;
    }

    //Método para validar login
    @PostMapping("/login")
    public ModelAndView validarLogin(@Valid UsuarioCliente usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceExc {
        ModelAndView mv = new ModelAndView();
        mv.addObject("email", new UsuarioCliente());

        if (br.hasErrors()) {
            mv.setViewName("login");
            return mv;
        }

        UsuarioCliente userLogin = serviceUsuario.loginUser(usuario.getEmail(), Util.md5(usuario.getSenha()));

        if (userLogin == null) {
            mv.addObject("msg", "Usuário não encontrado. Tente novamente!");
            mv.setViewName("login");
        } else {
            session.setAttribute("usuarioLogado", userLogin);
            mv.setViewName("redirect:/index"); // Redirecionando para a página inicial
        }

        return mv;
    }
}
