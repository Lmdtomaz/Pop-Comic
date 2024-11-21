package com.store.popcomic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID único para o pedido no banco

    @ManyToOne
    @JoinColumn(name = "cpf_cliente", foreignKey = @ForeignKey(name = "FK_cliente"))
    private Cliente cliente; // Cliente associado ao pedido (FK)

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "preco_unitario")
    private double precoUnitario;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "total_pedido")
    private double totalPedido;

    @Column(name = "valor_frete")
    private double valorFrete;

    @Column(name = "total_final")
    private double totalFinal;

    @Column(name = "opcao_pagamento")
    private String opcaoPagamento;

    // Getters e setters

    public Pedido() {}

    // Construtor para facilitar a criação de pedidos (opcional)
    public Pedido(Cliente cliente, String nomeProduto, int quantidade, double precoUnitario, double subtotal,
                  double totalPedido, double valorFrete, double totalFinal, String opcaoPagamento) {
        this.cliente = cliente;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
        this.totalPedido = totalPedido;
        this.valorFrete = valorFrete;
        this.totalFinal = totalFinal;
        this.opcaoPagamento = opcaoPagamento;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getCpf() +
                ", nomeProduto='" + nomeProduto + '\'' +
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
