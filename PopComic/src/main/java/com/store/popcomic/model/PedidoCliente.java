package com.store.popcomic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class PedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Geração automática do ID
    private Long id; // ID único para o PedidoCliente

    private String cpf;

    @OneToMany(mappedBy = "pedidoCliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos; // Lista de pedidos associados a este PedidoCliente

    private String status = "Pendente";

    private Double valorTotal;


    // Método adicional para adicionar pedidos à lista
    public void adicionarPedido(Pedido pedido) {
        if (this.pedidos == null) {
            this.pedidos = new ArrayList<>();
        }
        pedidos.add(pedido);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "PedidoCliente{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", pedidos=" + pedidos +
                ", status='" + status + '\'' +
                '}';
    }
}

