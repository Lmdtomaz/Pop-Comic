package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    // Lista para armazenar os produtos no carrinho (sem sessão)
    private List<Compra> carrinho = new ArrayList<>();

    // Método GET para exibir o carrinho
    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView modelAndView = new ModelAndView("carrinho");
        modelAndView.addObject("carrinho", carrinho); // Adiciona a lista de produtos ao carrinho
        return modelAndView;
    }

    // Método POST para adicionar o produto ao carrinho
    @PostMapping("/ecommerce/carrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id, String nomeProduto, double precoProduto) {
        System.out.println("PRODUTOOOOOOOOOOOO ID: " + id); // Valida se o ID está chegando ao método
        Compra compra = new Compra(nomeProduto, precoProduto); // Adiciona o nome e o preço do produto
        carrinho.add(compra);
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }
}
