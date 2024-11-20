package com.store.popcomic.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagamentoController { // Exibe a página de pagamento, validando se o usuário está logado
    @GetMapping("/pagamento")
    public String exibirPaginaPagamento(Model model, HttpSession session) {
        // Verifica se o usuário está logado
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login"; // Redireciona para a página de login, se não estiver logado
        }

        // Passa a opção de pagamento já selecionada para o modelo
        String opcaoPagamento = (String) session.getAttribute("opcaoPagamento");
        model.addAttribute("opcaoPagamento", opcaoPagamento);

        return "pagamento";  // Retorna para a página de pagamento
    }

    @PostMapping("/selecionarPagamento")
    public String selecionarPagamento(@RequestParam("opcaoPagamento") String opcaoPagamento,
                                      HttpSession session) {
        // Salva a opção de pagamento na sessão
        session.setAttribute("opcaoPagamento", opcaoPagamento);

        // Redireciona diretamente para a página de resumo do pedido, com a opção de pagamento já selecionada
        return "redirect:/finalizar-pedido";  // Página de resumo do pedido
    }

}