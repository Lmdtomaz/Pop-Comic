package com.store.popcomic.controller;

import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ProdutoRepository produtoRepository;

        @GetMapping("/")
        public ModelAndView index(){
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("produto", produtoRepository.findAll());
            return mv;
        }


}
