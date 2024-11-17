package com.store.popcomic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerCheckout {

    @GetMapping("/finalizar-pedido")
    public ModelAndView finalizarPedido(HttpSession session){
        if (session.getAttribute("usuarioLogado") == null){
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("resumo-pedido"); //Substituir com a pagina que da continuidade ao checkout
    }

}