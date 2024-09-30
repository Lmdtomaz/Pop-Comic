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
    // Método para desabilitar um produto (status "inativo")
    public void desabilitarProduto(int produtoId) throws SQLException {
        String desabilitarProdutoSQL = "UPDATE produtos SET status = 'inativo' WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(desabilitarProdutoSQL)) {
            ps.setInt(1, produtoId);
            ps.executeUpdate();
        }
    }

    // Método para reabilitar um produto
    public void reabilitarProduto(int produtoId) throws SQLException {
        String reabilitarProdutoSQL = "UPDATE produtos SET status = 'ativo' WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(reabilitarProdutoSQL)) {
            ps.setInt(1, produtoId);
            ps.executeUpdate();
        }
    }

    public void cadastrarProduto(Produto produto) throws SQLException {

        String inserirProdutoSQL = "INSERT INTO produtos (nome, avaliacao, descricaoDetalhada, precoProduto, qtdEstoque, status) VALUES (?, ?, ?, ?, ?, ?)";
        String inserirImagemSQL = "INSERT INTO imagens (produto_id, caminhoImagem, imagemPadrao) VALUES (?, ?, ?)";

        try (PreparedStatement psProduto = connection.prepareStatement(inserirProdutoSQL, new String[]{"id"})) {
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setDouble(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.setString(6, "ativo"); // Definindo o status como 'ativo' no momento do cadastro

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
                "FROM produtos p LEFT JOIN imagens i ON p.id = i.produto_id WHERE p.status = 'ativo'";

        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(listarProdutosSQL);
             ResultSet rs = ps.executeQuery()) {

            var produtosMap = new java.util.HashMap<Integer, Produto>();

            while (rs.next()) {
                int produtoId = rs.getInt("id");

                // Verifica se o produto já foi adicionado ao mapa de produtos
                Produto produto = produtosMap.get(produtoId);
                if (produto == null) {
                    // Se o produto ainda não foi adicionado, cria um novo objeto Produto e o adiciona ao mapa
                    produto = new Produto(
                            rs.getString("nome"),
                            rs.getDouble("avaliacao"),
                            rs.getString("descricaoDetalhada"),
                            rs.getDouble("precoProduto"),
                            rs.getInt("qtdEstoque"),
                            new ArrayList<>(), // Inicializa a lista de imagens vazia
                            rs.getString("caminhoImagem") // Define a primeira imagem como imagem padrão inicial
                    );
                    produtosMap.put(produtoId, produto); // Adiciona o produto ao mapa
                    produtos.add(produto); // Adiciona o produto à lista de produtos
                }

                // Adiciona as imagens associadas ao produto, se houver
                String caminhoImagem = rs.getString("caminhoImagem");
                if (caminhoImagem != null) {
                    produto.getImagens().add(caminhoImagem); // Adiciona a imagem à lista de imagens do produto
                    // Define a imagem como padrão, caso seja marcada como imagem principal
                    if (rs.getBoolean("imagemPadrao")) {
                        produto.setImagemPadrao(caminhoImagem);
                    }
                }
            }
        }

        // Retorna a lista de produtos ativos
        return produtos;
    }
}