package com.store.popcomic.controller;

import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private ProdutoRepository produtoRepository;

        @GetMapping("/")
        public ModelAndView index(){
            ModelAndView mv = new ModelAndView("/index");
            mv.addObject("listaProduto", produtoRepository.findAll());
            return mv;
        }


}
