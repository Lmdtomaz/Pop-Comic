package com.store.popcomic.controller;

import ch.qos.logback.core.model.Model;
import com.store.popcomic.model.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CarrinhoController {

    @GetMapping("adm/produtos/carrinho")
    public ModelAndView chamarCarrinho() {
        // Renderiza a página do carrinho (carrinho.html)
        ModelAndView mv = new ModelAndView("adm/produtos/carrinho");
        return mv;
    }

}
