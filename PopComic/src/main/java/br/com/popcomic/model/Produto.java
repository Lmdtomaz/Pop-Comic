package br.com.popcomic.model;

import java.util.List;

public class Produto {

    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private double precoProduto;
    private int qtdEstoque;
    private List<String> imagens;
    private String imagemPadrao;


    public Produto(String nome, double avaliacao, String descricaoDetalhada, double precoProduto, int qtdEstoque, List<String> imagens, String imagemPadrao) {
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.precoProduto = precoProduto;
        this.qtdEstoque = qtdEstoque;
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

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
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
