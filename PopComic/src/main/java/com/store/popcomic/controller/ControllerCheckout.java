package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.Map;


import java.util.List;

@Controller
public class ControllerCheckout {

    private final Map<String, Integer> opcoesFrete = Map.of(
            "Basico", 6,
            "Premium", 16,
            "Express", 30
    );

    @GetMapping("/finalizar-pedido")
    public ModelAndView finalizarPedido(String tipoFrete, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("carrinho");
            modelAndView.addObject("mensagemErro", "Seu carrinho está vazio!");
            return modelAndView;
        }

        // Calcular o total do pedido
        double totalPedido = carrinho.stream()
                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
                .sum();

        // Recupera o tipo de frete da sessão
        if (tipoFrete != null) {
            session.setAttribute("tipoFrete", tipoFrete); // Salva o tipo de frete na sessão
        } else {
            tipoFrete = (String) session.getAttribute("tipoFrete");
        }

        // Calcula o valor do frete
        double valorFrete = 0;
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            valorFrete = opcoesFrete.get(tipoFrete);
        }

        double totalFinal = totalPedido + valorFrete;

        // Formatar os valores com duas casas decimais
        DecimalFormat df = new DecimalFormat("0.00");
        String totalPedidoFormatado = df.format(totalPedido);
        String valorFreteFormatado = df.format(valorFrete);
        String totalFinalFormatado = df.format(totalFinal);

        // Passar os valores formatados para o modelo
        ModelAndView modelAndView = new ModelAndView("resumo-pedido");
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("totalPedido", totalPedidoFormatado);
        modelAndView.addObject("valorFrete", valorFreteFormatado);
        modelAndView.addObject("totalFinal", totalFinalFormatado);
        modelAndView.addObject("opcaoPagamento", session.getAttribute("opcaoPagamento"));

        return modelAndView;
    }


}