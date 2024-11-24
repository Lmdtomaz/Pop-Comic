package com.store.popcomic.controller;

import com.store.popcomic.model.Compra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FinalizarController {

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


}
