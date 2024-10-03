package com.store.popcomic.controller;

import com.store.popcomic.model.ItemCarrinho;
import com.store.popcomic.model.Produto;
import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ecommerce")
public class DetalhesController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("produto/detalhes/{id}")
    public ModelAndView visualizarProdutoDetalhes(@PathVariable("id") Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            ModelAndView mv = new ModelAndView("produto/detalhes");
            mv.addObject("produto", produto);
            mv.addObject("imagens", produto.getImagens());
            mv.addObject("avaliacao", produto.getAvaliacao());
            mv.addObject("preco", produto.getPreco());
            mv.addObject("descricao", produto.getDescricao());
            mv.addObject("nome", produto.getNome());
            return mv;
        }

        // Se o produto não for encontrado, redireciona para a landing page.
        return new ModelAndView("redirect:/");
    }


    @PostMapping("produto/comprar/{id}")
    public ModelAndView adicionarAoCarrinho(@PathVariable("id") Long id, @RequestParam("redirect") String redirect, HttpSession session) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            // Obtém o carrinho da sessão (ou cria um novo se não houver)
            List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new ArrayList<>();
            }

            // Verifica se o produto já está no carrinho
            boolean produtoExistente = false;
            for (ItemCarrinho item : carrinho) {
                if (item.getProduto().getId() == produto.getId()) {
                    item.incrementarQuantidade(); // Incrementa a quantidade do produto já existente
                    produtoExistente = true;
                    break;
                }
            }

            // Se o produto não estiver no carrinho, adiciona um novo item
            if (!produtoExistente) {
                carrinho.add(new ItemCarrinho(produto));
            }

            // Atualiza o carrinho na sessão
            session.setAttribute("carrinho", carrinho);
        }

        // Redireciona de acordo com o parâmetro 'redirect' ("carrinho" ou "continuar")
        if ("carrinho".equals(redirect)) {
            return new ModelAndView("redirect:/ecommerce/carrinho");
        } else {
            return new ModelAndView("redirect:/");  // Redireciona para a página inicial
        }
    }

}
