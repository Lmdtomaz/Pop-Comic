package com.store.popcomic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String bairro;
    private String infosAdicionais;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf", nullable = false)  // Definindo a chave estrangeira para o CPF do Cliente
    private Cliente cliente;




}
