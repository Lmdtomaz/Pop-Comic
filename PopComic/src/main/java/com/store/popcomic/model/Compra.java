package com.store.popcomic.model;

public class Compra {

    private Long id;

    private String nomeProduto;
    private double precoProduto;

    private String imagemProduto;

    private boolean status;

    private int quantidade;



    public Compra(Long id, String nomeProduto, double precoProduto, String imagemProduto, boolean status, int quantidade) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.imagemProduto = imagemProduto;
        this.status = status;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(String imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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

    // Método para retornar status como String
    public String getStatusString() {
        return status ? "Em estoque" : "Esgotado";
    }
}
