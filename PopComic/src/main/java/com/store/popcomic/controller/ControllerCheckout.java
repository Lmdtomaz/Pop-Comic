package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import com.store.popcomic.model.Pedido;
import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.PedidoCliente;
import com.store.popcomic.repository.PedidoClienteRepository;
import com.store.popcomic.repository.PedidoRepository;
import jakarta.servlet.http.HttpSession;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ControllerCheckout {

    @Autowired
    private PedidoRepository pedidoRepository;  // Repositório para salvar pedidos no banco'

    @Autowired
    private PedidoClienteRepository pedidoClienteRepository;

    // Mapa de opções de frete e seus respectivos valores
    private final Map<String, Integer> opcoesFrete = Map.of(
            "Basico", 6,
            "Premium", 16,
            "Express", 30
    );

    // Método para finalizar o pedido
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

    @PostMapping("/confirmar-pedido")
    public ModelAndView confirmarPedido(String tipoFrete, HttpSession session) {
        // Verifica se o usuário está logado
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Recupera o carrinho da sessão
        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("carrinho");
            modelAndView.addObject("mensagemErro", "Seu carrinho está vazio!");
            return modelAndView;
        }

        // Calcula o total do pedido
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

        // Recupera o cliente da sessão
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

        // Cria um novo PedidoCliente sem buscar pelo CPF
        PedidoCliente pedidoCliente = new PedidoCliente();
        pedidoCliente.setCpf(cliente.getCpf());  // Adiciona o CPF no PedidoCliente
        pedidoCliente.setPedidos(new ArrayList<>());  // Cria uma lista de pedidos vazia
        pedidoCliente.setValorTotal(totalFinal); // Atribui o total final ao PedidoCliente


        // Cria os pedidos a partir do carrinho
        for (Compra compra : carrinho) {
            Pedido pedido = new Pedido();
            pedido.setNomeProduto(compra.getNomeProduto());
            pedido.setQuantidade(compra.getQuantidade());
            pedido.setPrecoUnitario(compra.getPrecoProduto());
            pedido.setSubtotal(compra.getPrecoProduto() * compra.getQuantidade());
            pedido.setTotalPedido(totalPedido);
            pedido.setValorFrete(valorFrete);
            pedido.setTotalFinal(totalFinal);
            pedido.setOpcaoPagamento((String) session.getAttribute("opcaoPagamento"));
            pedido.setPedidoCliente(pedidoCliente);

            pedidoCliente.adicionarPedido(pedido);  // Adiciona o pedido ao PedidoCliente
        }

        // Salva o PedidoCliente, agora sempre criando um novo
        pedidoClienteRepository.save(pedidoCliente);

        // Limpar o carrinho da sessão após a finalização
        session.removeAttribute("carrinho");

        // Redireciona o usuário para a página /homeLogada
        return new ModelAndView("redirect:/homeLogada");
    }








}