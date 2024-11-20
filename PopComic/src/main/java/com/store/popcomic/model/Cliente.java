package com.store.popcomic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    private String cpf;

    private String nome;

    @Column(name = "data_nascimento") // Define o nome da coluna no banco, opcional
    private LocalDate dataNascimento;

    private String genero;

    private String senha; // Senha em formato hashed

    private String MainEnderecoFaturamento;

    private String MainNumeroResidenciaFaturamento;

    private String MainInfosAdicionaisResidenciaFaturamento;

    private String MainBairroFaturamento;

    private String MainCidadeFaturamento;

    private String MainEstadoFaturamento;

    private String Maincep;

    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
