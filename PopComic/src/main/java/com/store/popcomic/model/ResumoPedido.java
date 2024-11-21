package com.store.popcomic.model;

public class ResumoPedido {
    private String nomeProduto;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;
    private double totalPedido;
    private double valorFrete;
    private double totalFinal;
    private String opcaoPagamento;

    // Construtor
    public ResumoPedido(String nomeProduto, int quantidade, double precoUnitario, double subtotal,
                        double totalPedido, double valorFrete, double totalFinal, String opcaoPagamento) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
        this.totalPedido = totalPedido;
        this.valorFrete = valorFrete;
        this.totalFinal = totalFinal;
        this.opcaoPagamento = opcaoPagamento;
    }

    // Getters e Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }

    public String getOpcaoPagamento() {
        return opcaoPagamento;
    }

    public void setOpcaoPagamento(String opcaoPagamento) {
        this.opcaoPagamento = opcaoPagamento;
    }

    @Override
    public String toString() {
        return "ResumoPedido{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", subtotal=" + subtotal +
                ", totalPedido=" + totalPedido +
                ", valorFrete=" + valorFrete +
                ", totalFinal=" + totalFinal +
                ", opcaoPagamento='" + opcaoPagamento + '\'' +
                '}';
    }
}
