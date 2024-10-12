package com.store.popcomic.model;

public class ItemCarrinho {

    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto) {
        this.produto = produto;
        this.quantidade = 1; // Inicialmente, o item começa com quantidade 1
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void incrementarQuantidade() {
        this.quantidade++;
    }
}