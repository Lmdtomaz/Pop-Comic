package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CarrinhoController {

    // Lista para armazenar os produtos no carrinho (sem sessão)
    private List<Compra> carrinho = new ArrayList<>();

    // Mapa para armazenar as opções de frete
    private final Map<String, Integer> opcoesFrete = new HashMap<>() {{
        put("Basico", 6);
        put("Premium", 16);
        put("Express", 30);
    }};

    // Método GET para exibir o carrinho
    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView modelAndView = new ModelAndView("carrinho");
        modelAndView.addObject("carrinho", carrinho); // Adiciona a lista de produtos ao carrinho
        modelAndView.addObject("opcoesFrete", opcoesFrete); // Adiciona as opções de frete
        return modelAndView;
    }

    // Método POST para adicionar o produto ao carrinho
    @PostMapping("/ecommerce/carrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id, String imagem, String nomeProduto, boolean status, Integer quantidade, double precoProduto) {
        if (quantidade == null) {
            quantidade = 1; // Valor padrão
        }

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                compra.setQuantidade(compra.getQuantidade() + quantidade);
                return "redirect:/carrinho"; // Redireciona para a página do carrinho
            }
        }

        Compra compra = new Compra(id, nomeProduto, precoProduto, imagem, status, quantidade);
        carrinho.add(compra);
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    // Método POST para atualizar a quantidade do produto no carrinho
    @PostMapping("/carrinho/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Long id, int quantidade) {
        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                if (quantidade <= 0) {
                    carrinho.remove(compra);
                } else {
                    compra.setQuantidade(quantidade);
                }
                return "redirect:/carrinho"; // Redireciona para a página do carrinho
            }
        }
        return "redirect:/carrinho"; // Caso o produto não seja encontrado
    }

    // Método POST para remover o produto do carrinho
    @PostMapping("/carrinho/remover/{id}")
    public String removerProduto(@PathVariable Long id) {
        carrinho.removeIf(compra -> compra.getId().equals(id));
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    // Método POST para buscar frete com base na seleção do usuário
    @PostMapping("/carrinho/buscarFrete")
    public ModelAndView buscarFrete(String cep, String tipoFrete) {
        ModelAndView modelAndView = new ModelAndView("carrinho");
        modelAndView.addObject("carrinho", carrinho); // Adiciona a lista de produtos ao carrinho
        modelAndView.addObject("opcoesFrete", opcoesFrete); // Adiciona as opções de frete
        modelAndView.addObject("cep", cep); // Passa o CEP para a view
        modelAndView.addObject("freteSelecionado", opcoesFrete.get(tipoFrete)); // Adiciona o valor do frete selecionado
        return modelAndView;
    }
}