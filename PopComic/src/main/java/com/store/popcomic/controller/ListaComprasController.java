package com.store.popcomic.controller;

import com.store.popcomic.model.Pedido;
import com.store.popcomic.model.PedidoCliente;
import com.store.popcomic.repository.PedidoClienteRepository;
import com.store.popcomic.repository.PedidoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ListaComprasController {

    @Autowired
    private PedidoClienteRepository pedidoClienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/meus-pedidos")
    public ModelAndView listarPedidos(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login");
        }

        String cpf = (String) session.getAttribute("userCpf");

        if (cpf == null || cpf.isEmpty()) {
            ModelAndView mv = new ModelAndView("erroGenerico");
            mv.addObject("mensagemErro", "Não foi possível recuperar o CPF do usuário logado.");
            return mv;
        }

        // Busca os pedidos do cliente pelo CPF
        List<PedidoCliente> pedidoClientes = pedidoClienteRepository.findByCpf(cpf);

        for (PedidoCliente pedido : pedidoClientes){
            System.out.println(pedidoClientes);
        }

        ModelAndView modelAndView = new ModelAndView("listaPedidos");

        if (pedidoClientes.isEmpty()) {
            modelAndView.addObject("mensagemErro", "Você não possui pedidos cadastrados.");
        } else {
            modelAndView.addObject("pedidos", pedidoClientes);
        }

        return modelAndView;
    }


//    @GetMapping("/meus-pedidos")
//    public ModelAndView listarPedidos(HttpSession session) {
//        if (session.getAttribute("usuarioLogado") == null) {
//            return new ModelAndView("redirect:/login");
//        }
//
//        String cpf = (String) session.getAttribute("userCpf");
//        List<PedidoCliente> pedidoClientes = pedidoClienteRepository.findByCpf(cpf);
//
//        ModelAndView modelAndView = new ModelAndView("listaPedidos");
//        if (pedidoClientes == null || pedidoClientes.isEmpty()) {
//            modelAndView.addObject("mensagemErro", "Você não possui pedidos cadastrados.");
//        } else {
//            modelAndView.addObject("pedidos", pedidoClientes);
//        }
//
//        return modelAndView;
//    }


    @GetMapping("/pedidos/{id}")
    public String exibirDetalhesPedido(@PathVariable Long id, Model model) {
        // Busca o pedido pelo ID
        Pedido pedido = pedidoRepository.findById(id).orElse(null);

        if (pedido != null) {
            model.addAttribute("pedido", pedido);
            return "detalhesPedido"; // Página de detalhes do pedido
        } else {
            model.addAttribute("erro", "Pedido não encontrado.");
            return "redirect:/meus-pedidos"; // Redireciona para a lista de pedidos caso não encontre o pedido
        }
    }
}
