package com.store.popcomic.controller;

import com.store.popcomic.model.CarrinhoDeCompras;
import com.store.popcomic.model.ItemCarrinho;
import com.store.popcomic.model.Produto;
import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
        ModelAndView mv = new ModelAndView("adm/produtos/detalhes");
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            mv.addObject("produto", produto);
            mv.addObject("imagens", produto.getImagens());
            mv.addObject("avaliacao", produto.getAvaliacao());
            mv.addObject("preco", produto.getPreco());
            mv.addObject("descricao", produto.getDescricao());
            mv.addObject("nome", produto.getNome());
            return mv;
        }

        // Redireciona para a página inicial se o produto não for encontrado
        return new ModelAndView("redirect:/");
    }

//    @GetMapping("/produto/detalhes/{id}")
//    public ModelAndView visualizarProdutoDetalhes(@PathVariable("id") Long id) {
//        ModelAndView mv = new ModelAndView();
//
//        try {
//            Optional<Produto> produtoOpt = produtoRepository.findById(id);
//
//            if (produtoOpt.isPresent()) {
//                Produto produto = produtoOpt.get();
//                if (produto.getImagens() == null || produto.getImagens().isEmpty()) {
//                    produto.setImagens(new ArrayList<>()); // Ou outra lógica adequada
//                }
//
//                mv.addObject("produto", produto);
//                mv.addObject("imagens", produto.getImagens());
//                mv.addObject("avaliacao", produto.getAvaliacao());
//                mv.addObject("preco", produto.getPreco());
//                mv.addObject("descricao", produto.getDescricao());
//                mv.addObject("nome", produto.getNome());
//                mv.setViewName("produto/detalhes");
//            } else {
//                // Redireciona se o produto não for encontrado
//                mv.setViewName("redirect:/");
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Imprime a stack trace no console para depuração
//            mv.setViewName("error"); // Redireciona para uma página de erro ou uma página específica que você cria
//            mv.addObject("mensagem", "Ocorreu um erro ao tentar visualizar os detalhes do produto: " + e.getMessage());
//        }
//
//        return;
//    }

    @PostMapping("/produto/comprar/{id}")
    public ModelAndView adicionarAoCarrinho(@PathVariable("id") Long id, @RequestParam("redirect") String redirect) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            // Adiciona o produto ao carrinho de compras
            carrinhoDeCompras.adicionarItem(new ItemCarrinho(produto));
        }

        // Redireciona de acordo com a opção escolhida
        if ("carrinho".equals(redirect)) {
            return new ModelAndView("redirect:/ecommerce/carrinho");
        } else {
            return new ModelAndView("redirect:/");
        }
    }
}