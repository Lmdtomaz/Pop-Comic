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
    public String adicionarCarrinho(@PathVariable Long id, String imagem, String nomeProduto, boolean status, Integer quantidade, double precoProduto) {
        // Se quantidade for null, você pode definir um valor padrão, se necessário
        if (quantidade == null) {
            quantidade = 1; // Ou outro valor padrão que você desejar
        }

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                // Se o produto já estiver no carrinho, apenas aumenta a quantidade
                compra.setQuantidade(compra.getQuantidade() + quantidade);
                return "redirect:/carrinho"; // Redireciona para a página do carrinho
            }
        }

        Compra compra = new Compra(id, nomeProduto, precoProduto, imagem, status, quantidade); // Adiciona o nome e o preço do produto
        carrinho.add(compra);
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    // Método POST para atualizar a quantidade do produto no carrinho
    @PostMapping("/carrinho/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Long id, int quantidade) {
        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                if (quantidade <= 0) {
                    carrinho.remove(compra); // Remove o produto se a quantidade for 0 ou menor
                } else {
                    compra.setQuantidade(quantidade); // Atualiza a quantidade
                }
                return "redirect:/carrinho"; // Redireciona para a página do carrinho
            }
        }
        return "redirect:/carrinho"; // Caso o produto não seja encontrado, redireciona
    }

    // Método POST para remover o produto do carrinho
    @PostMapping("/carrinho/remover/{id}")
    public String removerProduto(@PathVariable Long id) {
        carrinho.removeIf(compra -> compra.getId().equals(id));
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }
}
