//package com.store.popcomic.controller;
//
//import com.store.popcomic.model.Cliente;
//import com.store.popcomic.model.Endereco;
//import com.store.popcomic.model.Endereco;
//import com.store.popcomic.repository.ClienteRepository;                                EMANUEL
//import com.store.popcomic.repository.EnderecoRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/ecommerce")
//public class EnderecoController {
//
//    @Autowired
//    private EnderecoRepository enderecoRepository;
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    // Método para cadastrar um novo endereço
//    @PostMapping("/salvarEndereco")
//    public String adicionarEndereco(@RequestParam String cpf, @RequestBody Endereco end, @ModelAttribute Endereco endereco, HttpSession session, Model model) {
//        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
//
//        if (clienteLogado == null) {
//            return "redirect:/login"; // Redireciona para login caso o cliente não esteja logado
//        }
//
//        // Associando o cliente ao novo endereço
//        endereco.setCliente(clienteLogado);
//
//        // Salvando o endereço no banco de dados
//        enderecoRepository.save(endereco);
//
//        // Redirecionando para a página de escolha de endereço após salvar
//        return "redirect:/perfil";
//    }
//}
