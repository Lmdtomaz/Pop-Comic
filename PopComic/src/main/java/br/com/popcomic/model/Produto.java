package br.com.popcomic.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Produto {

    private int id;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private BigDecimal precoProduto;
    private int qtdEstoque;
    private boolean status;
    private List<String> imagens;
    private String imagemPadrao;


    public Produto(String nome, BigDecimal precoProduto, int qtdEstoque, String descricaoDetalhada, double avaliacao) {
        this.nome = nome;
        this.precoProduto = precoProduto;
        this.qtdEstoque = qtdEstoque;
        this.descricaoDetalhada = descricaoDetalhada;
        this.avaliacao = avaliacao;
    }



    public Produto(int id, String nome, double avaliacao, String descricaoDetalhada, BigDecimal precoProduto, int qtdEstoque, List<String> imagens, String imagemPadrao) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.precoProduto = precoProduto;
        this.qtdEstoque = qtdEstoque;
        this.imagens = imagens;
        this.imagemPadrao = imagemPadrao;
        this.status = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto(int id, String nome, double avaliacao, String descricaoDetalhada, BigDecimal precoProduto, int qtdEstoque, boolean status, List<String> imagens, String imagemPadrao) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.precoProduto = precoProduto;
        this.qtdEstoque = qtdEstoque;
        this.status = status;
        this.imagens = imagens;
        this.imagemPadrao = imagemPadrao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        if (avaliacao >= 0.5 && avaliacao <= 5 && avaliacao % 0.5 == 0) {
            this.avaliacao = avaliacao;
        } else {
            throw new IllegalArgumentException("Avaliação deve ser entre 0.5 e 5, variando de 0.5 em 0.5.");
        }
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        if (descricaoDetalhada.length() <= 2000) {
            this.descricaoDetalhada = descricaoDetalhada;
        } else {
            throw new IllegalArgumentException("Descrição detalhada deve ter no máximo 2000 caracteres.");
        }
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public String getImagemPadrao() {
        return imagemPadrao;
    }

    public void setImagemPadrao(String imagemPadrao) {
        this.imagemPadrao = imagemPadrao;
    }
}
