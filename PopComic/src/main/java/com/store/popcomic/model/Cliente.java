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

    private String MainEndereco;

    private String MainNumeroResidencia;

    private String MainInfosAdicionaisResidencia;

    private String Maincep;

    private String email;

//    @OneToMany(mappedBy = "cliente")
//    private List<Endereco> enderecos;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMainEndereco() {
        return MainEndereco;
    }

    public void setMainEndereco(String mainEndereco) {
        MainEndereco = mainEndereco;
    }

    public String getMainNumeroResidencia() {
        return MainNumeroResidencia;
    }

    public void setMainNumeroResidencia(String mainNumeroResidencia) {
        MainNumeroResidencia = mainNumeroResidencia;
    }

    public String getMainInfosAdicionaisResidencia() {
        return MainInfosAdicionaisResidencia;
    }

    public void setMainInfosAdicionaisResidencia(String mainInfosAdicionaisResidencia) {
        MainInfosAdicionaisResidencia = mainInfosAdicionaisResidencia;
    }

    public String getMaincep() {
        return Maincep;
    }

    public void setMaincep(String maincep) {
        Maincep = maincep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//    public List<Endereco> getEnderecos() {
//        return enderecos;
//    }
//
//    public void setEnderecos(List<Endereco> enderecos) {
//        this.enderecos = enderecos;
//    }
}
