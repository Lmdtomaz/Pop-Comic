package com.store.popcomic.controller;

import com.store.popcomic.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarrinhoService {

    private List<Produto> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        itens.add(produto);
    }

    public List<Produto> getItens() {
        return itens;
    }
}
