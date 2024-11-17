package com.store.popcomic.controller;

import com.store.popcomic.model.CarrinhoDeCompras;
import com.store.popcomic.model.ItemCarrinho;
import com.store.popcomic.model.Produto;
import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/ecommerce")
public class DetalhesController {



    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoDeCompras carrinhoDeCompras;



    @GetMapping("/produto/detalhes/{id}")
    public ModelAndView visualizarProdutoDetalhes(@PathVariable("id") Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            ModelAndView mv = new ModelAndView("detalhes");
            mv.addObject("produto", produto);
            mv.addObject("imagens", produto.getImagens());
            mv.addObject("avaliacao", produto.getAvaliacao());
            mv.addObject("preco", produto.getPreco());
            mv.addObject("descricao", produto.getDescricao());
            mv.addObject("nome", produto.getNome());
            return mv;
        }

        return new ModelAndView("redirect:/");  // Redireciona para a página inicial se o produto não for encontrado
    }

    @PostMapping("/produto/comprar/{id}")
    public ModelAndView adicionarAoCarrinho(@PathVariable("id") Long id, @RequestParam("redirect") String redirect) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            // Adiciona o produto ao carrinho de compras
            carrinhoDeCompras.adicionarItem(new ItemCarrinho(produto));
        }

        if ("carrinho".equals(redirect)) {
            return new ModelAndView("redirect:/ecommerce/carrinho");
        } else {
            return new ModelAndView("redirect:/");  // Redireciona para a página inicial
        }
    }
}