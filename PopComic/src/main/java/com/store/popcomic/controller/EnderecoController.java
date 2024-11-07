//package com.store.popcomic.controller;
//
//import com.store.popcomic.model.Cliente;
//import com.store.popcomic.model.Enderecos;
//import com.store.popcomic.repository.ClienteRepository;
//import com.store.popcomic.repository.EnderecoRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/ecommerce/enderecos")
//public class EnderecoController {
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private EnderecoRepository enderecoRepository;
//
//
//    @GetMapping("/checkout")
//    public ModelAndView escolherEnderecoEntrega(HttpSession session) {
//        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
//        if (clienteLogado == null) {
//            return new ModelAndView("redirect:/login");
//        }
//
//        ModelAndView modelAndView = new ModelAndView("escolherEndereco");
//        modelAndView.addObject("enderecos", enderecoRepository.findByClienteCpf(clienteLogado.getCpf()));
//        return modelAndView;
//    }
//
//    @PostMapping("/checkout/selecionar")
//    public String selecionarEnderecoEntrega(@RequestParam Long enderecoId, HttpSession session, Model model) {
//        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
//        if (clienteLogado == null) {
//            return "redirect:/login";
//        }
//
//        Optional<Enderecos> enderecoSelecionadoOpt = enderecoRepository.findById(enderecoId);
//        if (enderecoSelecionadoOpt.isEmpty() || !enderecoSelecionadoOpt.get().getCliente().equals(clienteLogado)) { //
//            model.addAttribute("erro", "Endereço inválido.");
//            return "escolherEndereco";
//        }
//
//        Enderecos enderecoSelecionado = enderecoSelecionadoOpt.get();
//        session.setAttribute("enderecoEntrega", enderecoSelecionado);
//        return "redirect:/ecommerce/checkout/pagamento";
//    }
//    @GetMapping("/checkout/pagamento")
//    public String escolherFormaPagamento(HttpSession session, Model model) {
//        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
//        Enderecos enderecoEntrega = (Enderecos) session.getAttribute("enderecoEntrega");
//
//        if (clienteLogado == null) {
//            return "redirect:/login";
//        }
//        if (enderecoEntrega == null) {
//            model.addAttribute("erro", "É necessário escolher um endereço de entrega antes de continuar.");
//            return "escolherEndereco";
//        }
//
//        return "escolherPagamento";
//    }
//
//
//}