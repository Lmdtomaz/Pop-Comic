package br.com.popcomic.dao;

import br.com.popcomic.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private Connection connection;


    public ProdutoDao(Connection connection) {
        this.connection = connection;
    }


    public void cadastrarProduto(Produto produto) throws SQLException {
        String inserirProdutoSQL = "INSERT INTO produtos (nome, avaliacao, descricaoDetalhada, precoProduto, qtdEstoque) VALUES (?, ?, ?, ?, ?)";
        String inserirImagemSQL = "INSERT INTO imagens (produto_id, caminhoImagem, imagemPadrao) VALUES (?, ?, ?)";


        try (PreparedStatement psProduto = connection.prepareStatement(inserirProdutoSQL, new String[]{"id"})) {
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setDouble(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.executeUpdate();


            try (var rs = psProduto.getGeneratedKeys()) {
                if (rs.next()) {
                    int produtoId = rs.getInt(1);


                    try (PreparedStatement psImagem = connection.prepareStatement(inserirImagemSQL)) {
                        for (String imagem : produto.getImagens()) {
                            psImagem.setInt(1, produtoId);
                            psImagem.setString(2, imagem);
                            psImagem.setBoolean(3, imagem.equals(produto.getImagemPadrao()));
                            psImagem.addBatch();
                        }
                        psImagem.executeBatch();
                    }
                }
            }
        }
    }
    public List<Produto> listarProdutos() throws SQLException {
        String listarProdutosSQL = "SELECT p.id, p.nome, p.avaliacao, p.descricaoDetalhada, p.precoProduto, p.qtdEstoque, i.caminhoImagem, i.imagemPadrao " +
                "FROM produtos p LEFT JOIN imagens i ON p.id = i.produto_id";

        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(listarProdutosSQL);
             ResultSet rs = ps.executeQuery()) {

            // Mapeia produtos por ID para tratar imagens associadas
            var produtosMap = new java.util.HashMap<Integer, Produto>();

            while (rs.next()) {
                int produtoId = rs.getInt("id");

                // Verifica se o produto já foi adicionado à lista
                Produto produto = produtosMap.get(produtoId);
                if (produto == null) {
                    produto = new Produto(
                            rs.getString("nome"),
                            rs.getDouble("avaliacao"),
                            rs.getString("descricaoDetalhada"),
                            rs.getDouble("precoProduto"),
                            rs.getInt("qtdEstoque"),
                            new ArrayList<>(), // Lista de imagens inicializada vazia
                            rs.getString("caminhoImagem") // Definido aqui como padrão para inicialização
                    );
                    produtosMap.put(produtoId, produto);
                    produtos.add(produto);
                }

                // Adiciona imagem se houver
                String caminhoImagem = rs.getString("caminhoImagem");
                if (caminhoImagem != null) {
                    produto.getImagens().add(caminhoImagem);
                    if (rs.getBoolean("imagemPadrao")) {
                        produto.setImagemPadrao(caminhoImagem);
                    }
                }
            }
        }
        return produtos;
    }
}
