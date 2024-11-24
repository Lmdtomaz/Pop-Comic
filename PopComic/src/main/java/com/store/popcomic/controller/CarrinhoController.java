package com.store.popcomic.controller;
import com.store.popcomic.model.Compra;
import com.store.popcomic.repository.PedidoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CarrinhoController {

    // Lista para armazenar os produtos no carrinho (sem sessão)
    private List<Compra> carrinho = new ArrayList<>();


    @Autowired
    PedidoClienteRepository pedidoClienteRepository;

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
        List<Compra> carrinho = getCarrinho(session);

        // Calcular o total do pedido
        double totalPedido = carrinho.stream()
                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
                .sum();

        // Recuperar o tipo de frete da sessão
        String tipoFrete = (String) session.getAttribute("tipoFrete");

        // Calcular o valor do frete
        double valorFrete = 0;
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            valorFrete = opcoesFrete.get(tipoFrete);
        }

        // Calcular o total final
        double totalFinal = totalPedido + valorFrete;

        // Formatar os valores com duas casas decimais
        DecimalFormat df = new DecimalFormat("0.00");
        String totalPedidoFormatado = df.format(totalPedido);
        String valorFreteFormatado = df.format(valorFrete);
        String totalFinalFormatado = df.format(totalFinal);

        // Passar os valores formatados para o modelo
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("totalPedido", totalPedidoFormatado);
        modelAndView.addObject("valorFrete", valorFreteFormatado);
        modelAndView.addObject("totalFinal", totalFinalFormatado);
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

    @PostMapping("/carrinho/aumentar/{id}")
    public String aumentarQuantidade(@PathVariable Long id, HttpSession session) {
        List<Compra> carrinho = getCarrinho(session);

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                compra.setQuantidade(compra.getQuantidade() + 1);
                break;
            }
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/diminuir/{id}")
    public String diminuirQuantidade(@PathVariable Long id, HttpSession session) {
        List<Compra> carrinho = getCarrinho(session);

        for (Compra compra : carrinho) {
            if (compra.getId().equals(id)) {
                if (compra.getQuantidade() > 1) {
                    compra.setQuantidade(compra.getQuantidade() - 1);
                } else {
                    carrinho.remove(compra);
                }
                break;
            }
        }
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

        // Calcular o total do pedido
        double totalPedido = carrinho.stream()
                .mapToDouble(compra -> compra.getPrecoProduto() * compra.getQuantidade())
                .sum();

        // Recuperar o tipo de frete da sessão
        String tipoFrete = (String) session.getAttribute("tipoFrete");

        // Calcular o valor do frete
        double valorFrete = 0;
        if (tipoFrete != null && opcoesFrete.containsKey(tipoFrete)) {
            valorFrete = opcoesFrete.get(tipoFrete);
        }

        // Calcular o total final
        double totalFinal = totalPedido + valorFrete;

        // Formatar os valores com duas casas decimais
        DecimalFormat df = new DecimalFormat("0.00");
        String totalPedidoFormatado = df.format(totalPedido);
        String valorFreteFormatado = df.format(valorFrete);
        String totalFinalFormatado = df.format(totalFinal);

        // Passar os valores formatados para o modelo
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("totalPedido", totalPedidoFormatado);
        modelAndView.addObject("valorFrete", valorFreteFormatado);
        modelAndView.addObject("totalFinal", totalFinalFormatado);

        return modelAndView;
    }


}