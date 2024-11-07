package com.store.popcomic.controller;

import com.store.popcomic.repository.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ProdutoRepository produtoRepository;

        @GetMapping("/")
        public ModelAndView index(HttpSession session){
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("produto", produtoRepository.findAll());

            if (session.getAttribute("usuarioLogado") != null) {
                // O usuário está logado, você pode adicionar atributos ao modelo se necessário
                mv.addObject("usuario", session.getAttribute("usuarioLogado"));
            }
            return mv;
        }






}
