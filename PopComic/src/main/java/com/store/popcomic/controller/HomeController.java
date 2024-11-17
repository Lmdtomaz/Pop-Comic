package com.store.popcomic.controller;

import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.store.popcomic.repository.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping("/homeLogada")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("homeLogada");
        mv.addObject("produto", produtoRepository.findAll());
        return mv;
    }
}
