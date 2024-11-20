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
        List<Compra> carrinho = getCarrinho(session);

        if (quantidade == null) {
            quantidade = 1;
        }

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                compra.setQuantidade(compra.getQuantidade() + quantidade);
                return "redirect:/carrinho";
            }
        }

        Compra compra = new Compra(id, nomeProduto, precoProduto, imagem, status, quantidade);
        carrinho.add(compra);
        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Long id, int quantidade, HttpSession session) {
        List<Compra> carrinho = getCarrinho(session);

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                if (quantidade <= 0) {
                    carrinho.remove(compra);
                } else {
                    compra.setQuantidade(quantidade);
                }
                return "redirect:/carrinho";
            }
        }
        return "redirect:/carrinho";
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

        return modelAndView;
    }


}