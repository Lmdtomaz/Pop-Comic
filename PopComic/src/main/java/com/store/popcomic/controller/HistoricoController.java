//package com.store.popcomic.controller;
//
//import com.store.popcomic.model.Historico;
//import com.store.popcomic.repository.HistoricoRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class HistoricoController {
//
//    private final HistoricoRepository historicoRepository;
//
//    public HistoricoController(HistoricoRepository historicoRepository) {
//        this.historicoRepository = historicoRepository;
//    }
//
//    @GetMapping("/historico-pedidos")
//    public String historicoPedidos(HttpSession session, Model model) {
//        // Verificar se o usuário está logado
//        if (session.getAttribute("usuarioLogado") == null) {
//            return "redirect:/login"; // Redireciona para login se não estiver logado
//        }
//
//        // Recuperar o CPF do usuário da sessão
//        String cpfUsuario = (String) session.getAttribute("cpf");
//
//        // Buscar histórico de pedidos pelo CPF do cliente
//        List<Historico> pedidos = historicoRepository.findByClienteCpf(cpfUsuario);
//
//        // Adicionar os pedidos ao modelo
//        model.addAttribute("pedidos", pedidos);
//
//        return "historico-pedidos"; // Nome da view
//    }
//}
