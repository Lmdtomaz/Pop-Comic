//package com.store.popcomic.model;
//
//
//import com.store.popcomic.model.Cliente;
//import com.store.popcomic.model.Historico;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//public class Historico {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "cliente_cpf", nullable = false)
//    private Cliente cliente; // Relacionamento com a entidade Cliente
//
////    @OneToMany(mappedBy = "historico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
////    private List<Compra> compras; // Lista de compras feitas pelo cliente no histórico
//
//    private String tipoFrete; // Tipo de frete escolhido
//
//    private double valorFrete; // Valor do frete
//
//    private double totalPedido; // Total do pedido (sem o frete)
//
//    private double totalFinal; // Total final (com o frete)
//
//    private String opcaoPagamento; // Forma de pagamento escolhida
//
//    private String enderecoEntrega; // Endereço de entrega do pedido
//
//}
