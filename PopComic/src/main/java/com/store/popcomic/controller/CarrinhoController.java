package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

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


    private List<Compra> getCarrinho(HttpSession session) {
        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("carrinho");
        modelAndView.addObject("carrinho", getCarrinho(session)); // Obtém o carrinho da sessão
        modelAndView.addObject("opcoesFrete", opcoesFrete);
        return modelAndView;
    }

    @PostMapping("/ecommerce/carrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id, String imagem, String nomeProduto, boolean status, Integer quantidade, double precoProduto, HttpSession session) {
        // Definindo um valor padrão para a quantidade, caso ela seja nula ou negativa
        if (quantidade == null || quantidade <= 0) {
            quantidade = 1;  // Define a quantidade como 1 se não for válida
        }

        List<Compra> carrinho = getCarrinho(session);

        // Verifica se o produto já existe no carrinho e atualiza a quantidade
        Compra produtoExistente = carrinho.stream()
                .filter(compra -> compra.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (produtoExistente != null) {
            produtoExistente.setQuantidade(produtoExistente.getQuantidade() + quantidade);
        } else {
            Compra novaCompra = new Compra(id, nomeProduto, precoProduto, imagem, status, quantidade);
            carrinho.add(novaCompra);
        }

        // Atualiza o carrinho na sessão
        session.setAttribute("carrinho", carrinho);

        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Long id, int quantidade, HttpSession session) {
        List<Compra> carrinho = getCarrinho(session); // Obtém o carrinho da sessão

        // Verifica se a quantidade é válida
        if (quantidade <= 0) {
            // Se a quantidade for menor ou igual a zero, o produto será removido
            carrinho.removeIf(compra -> compra.getId().equals(id));
        } else {
            // Se o produto já estiver no carrinho, apenas atualiza a quantidade
            for (Compra compra : carrinho) {
                if (compra.getId().equals(id)) {
                    compra.setQuantidade(quantidade); // Atualiza a quantidade
                    break; // Encerra o loop após atualizar a quantidade
                }
            }
        }

        // Atualiza a sessão com o carrinho modificado
        session.setAttribute("carrinho", carrinho);

        return "redirect:/carrinho"; // Redireciona para a página do carrinho após a atualização
    }

    @PostMapping("/carrinho/remover/{id}")
    public String removerProduto(@PathVariable Long id, HttpSession session) {
        List<Compra> carrinho = getCarrinho(session);
        carrinho.removeIf(compra -> compra.getId().equals(id));
        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/selecionarFrete")
    public String selecionarFrete(String tipoFrete, HttpSession session) {
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            session.setAttribute("tipoFrete", tipoFrete);
        }
        return "redirect:/carrinho"; // Ou redireciona para a página do carrinho após seleção
    }

    @GetMapping("/resumo-pedido")
    public ModelAndView mostrarResumoPedido(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("resumo-pedido");
        List<Compra> carrinho = getCarrinho(session);

        double totalPedido = carrinho.stream()
                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
                .sum();

        String tipoFrete = (String) session.getAttribute("tipoFrete");

        double valorFrete = 0;
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            valorFrete = opcoesFrete.get(tipoFrete);
        }

        double totalFinal = totalPedido + valorFrete;

        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("totalPedido", totalPedido);
        modelAndView.addObject("valorFrete", valorFrete);
        modelAndView.addObject("totalFinal", totalFinal);

        // Agora adicionamos as quantidades de cada produto no carrinho para o modelo
        modelAndView.addObject("quantidades", carrinho.stream()
                .map(compra -> compra.getQuantidade()) // Extrai as quantidades
                .toArray());

        return modelAndView;
    }
}
