package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import com.store.popcomic.model.Pedido;
import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.PedidoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerCheckout {

    @Autowired
    private PedidoRepository pedidoRepository;  // Repositório para salvar pedidos no banco

    // Mapa de opções de frete e seus respectivos valores
    private final Map<String, Integer> opcoesFrete = Map.of(
            "Basico", 6,
            "Premium", 16,
            "Express", 30
    );

    // Método para finalizar o pedido
    @GetMapping("/finalizar-pedido")
    public ModelAndView finalizarPedido(String tipoFrete, HttpSession session) {
        // Verifica se o usuário está logado
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Recupera o carrinho da sessão
        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");

        // Verifica se o carrinho está vazio
        if (carrinho == null || carrinho.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("carrinho");
            modelAndView.addObject("mensagemErro", "Seu carrinho está vazio!");
            return modelAndView;
        }

        // Calcula o total do pedido
        double totalPedido = carrinho.stream()
                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
                .sum();

        // Recupera o tipo de frete da sessão, ou usa o tipo recebido como parâmetro
        if (tipoFrete != null) {
            session.setAttribute("tipoFrete", tipoFrete); // Salva o tipo de frete na sessão
        } else {
            tipoFrete = (String) session.getAttribute("tipoFrete");
        }

        // Calcula o valor do frete com base na opção escolhida
        double valorFrete = 0;
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            valorFrete = opcoesFrete.get(tipoFrete);
        }

        // Calcula o total final incluindo o frete
        double totalFinal = totalPedido + valorFrete;

        // Recupera a opção de pagamento da sessão
        String opcaoPagamento = (String) session.getAttribute("opcaoPagamento");

        // Recupera o cliente logado da sessão
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

        // Adiciona cada item do carrinho como um pedido no banco de dados
        for (Compra compra : carrinho) {
            // Criação do objeto Pedido para cada item
            Pedido pedido = new Pedido(
                    cliente,  // Cliente logado
                    compra.getNomeProduto(),  // Nome do produto
                    compra.getQuantidade(),  // Quantidade do produto
                    compra.getPrecoProduto(),  // Preço unitário
                    compra.getPrecoProduto() * compra.getQuantidade(),  // Preço total do produto
                    totalPedido,  // Total do pedido (sem frete)
                    valorFrete,  // Valor do frete
                    totalFinal,  // Total final (pedido + frete)
                    opcaoPagamento  // Opção de pagamento
            );
            pedidoRepository.save(pedido);  // Salva o pedido no banco de dados
        }

        // Prepara os dados para a página de resumo do pedido
        ModelAndView modelAndView = new ModelAndView("resumo-pedido");
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("totalPedido", totalPedido);
        modelAndView.addObject("valorFrete", valorFrete);
        modelAndView.addObject("totalFinal", totalFinal);
        modelAndView.addObject("opcaoPagamento", opcaoPagamento);

        // Retorna a visão de resumo do pedido
        return modelAndView;
    }
}
