package com.store.popcomic.model;

public class Compra {

    private String nomeProduto;
    private double precoProduto;

    public Compra(String nomeProduto, double precoProduto) {
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
    }

    // Getters e Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }
}
