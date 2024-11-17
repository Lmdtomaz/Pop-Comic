package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FinalizarController {

    private final Map<String, Integer> opcoesFrete = new HashMap<>() {{
        put("Basico", 6);
        put("Premium", 16);
        put("Express", 30);
    }};

    // Método para obter o carrinho da sessão
    private List<Compra> getCarrinho(HttpSession session) {
        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

//    @GetMapping("/finalizar-pedido")
//    public ModelAndView chamaFinalizar(HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("finalizar");
//
//        // Obtém o carrinho da sessão
//        List<Compra> carrinho = getCarrinho(session);
//
//        // Calcula o total dos produtos
//        double totalProdutos = carrinho.stream()
//                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
//                .sum();
//
//        // Passa o carrinho e o total para a view
//        modelAndView.addObject("carrinho", carrinho);
//        modelAndView.addObject("totalProdutos", totalProdutos);
//
//        // Se houver frete selecionado, adicione o valor do frete
//        // No seu caso, você pode recuperar isso da sessão ou de outro local
//        String tipoFrete = (String) session.getAttribute("tipoFrete");
//        int frete = opcoesFrete.getOrDefault(tipoFrete, 0);
//        modelAndView.addObject("frete", frete);
//        modelAndView.addObject("totalFinal", totalProdutos + frete);
//
//        return modelAndView;
//    }
}
