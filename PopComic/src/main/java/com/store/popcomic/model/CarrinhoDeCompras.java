package com.store.popcomic.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope  // Define que este bean será específico para a sessão do usuário
public class CarrinhoDeCompras {

    private List<ItemCarrinho> itens = new ArrayList<>();

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void adicionarItem(ItemCarrinho item) {
        for (ItemCarrinho carrinhoItem : itens)
            if (carrinhoItem.getProduto().getId() == item.getProduto().getId()) {  // Corrige a comparação do ID
                carrinhoItem.incrementarQuantidade();
                return;
            }
        this.itens.add(item);
    }
}
