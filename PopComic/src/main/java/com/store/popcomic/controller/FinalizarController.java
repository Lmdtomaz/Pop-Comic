package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FinalizarController {

    // Lista para armazenar os produtos no carrinho (sem sessão)
    private List<Compra> carrinho = new ArrayList<>();

    // Mapa para armazenar as opções de frete

    private final Map<String, Integer> opcoesFrete = new HashMap<>() {{
        put("Basico", 6);
        put("Premium", 16);
        put("Express", 30);
    }};

    // Método GET para exibir a tela de finalizar compra
//    @GetMapping("/finalizar")
//    public ModelAndView chamaFinalizar() {
//        ModelAndView modelAndView = new ModelAndView("Finalizar");
//        modelAndView.addObject("carrinho", carrinho); // Adiciona a lista de produtos ao carrinho
//        modelAndView.addObject("opcoesFrete", opcoesFrete); // Adiciona as opções de frete
//        return modelAndView;
//    }

//    public ModelAndView finalizarPedido(HttpSession session){
//        if (session.getAttribute("usuarioLogado") == null){
//            return new ModelAndView("redirect:/login");
//        }
//        return new ModelAndView(""); //Substituir com a pagina que da continuidade ao checkout
//    }
    @GetMapping("/finalizar")
    public ModelAndView chamaFinalizar(HttpSession session) {
        List<Compra> carrinho = (List<Compra>) session.getAttribute("carrinho");

        ModelAndView modelAndView = new ModelAndView("Finalizar");
        modelAndView.addObject("carrinho", carrinho != null ? carrinho : new ArrayList<>()); // Adiciona a lista de produtos ao carrinho
        return modelAndView;
    }

    // Método para finalizar o pedido
    public ModelAndView finalizarPedido(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView(""); // Substituir com a página que dá continuidade ao checkout
    }
}
