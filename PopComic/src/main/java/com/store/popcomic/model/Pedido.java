package com.store.popcomic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // ID único para o pedido no banco

    @ManyToOne
    @JoinColumn(name = "pedido_cliente_id", foreignKey = @ForeignKey(name = "FK_pedido_cliente"))
    private PedidoCliente pedidoCliente; // Relacionamento com PedidoCliente

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

    // Construtor sem argumentos
    public Pedido() {}

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
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
