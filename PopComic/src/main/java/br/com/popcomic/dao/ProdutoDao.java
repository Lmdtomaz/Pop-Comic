package br.com.popcomic.dao;

import br.com.popcomic.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProdutoDao {

    private Connection connection;

    public ProdutoDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrarProduto(Produto produto) throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Erro: Conexão com o banco de dados não estabelecida.");
            return false;
        }

        String inserirProdutoSQL = "INSERT INTO Produto (nome, avaliacao, descricaoDetalhada, precoProduto, qtdEstoque, imagemPadrao, imagens) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psProduto = connection.prepareStatement(inserirProdutoSQL)) {
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setBigDecimal(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.setString(6, produto.getImagemPadrao());

            // Validação de imagens
            String imagensConcatenadas = produto.getImagens() != null ? String.join(",", produto.getImagens()) : "";
            psProduto.setString(7, imagensConcatenadas);

            int linhasAfetadas = psProduto.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false caso ocorra um erro
        }
    }

    public List<Produto> listarProdutos() throws SQLException {
        String listarProdutosSQL = "SELECT * FROM Produto";

        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(listarProdutosSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Obtém os dados básicos do produto
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("avaliacao"),
                        rs.getString("descricaoDetalhada"),
                        rs.getBigDecimal("precoProduto"),
                        rs.getInt("qtdEstoque"),
                        rs.getBoolean("status"),
                        new ArrayList<>(), // Inicializa a lista de imagens
                        rs.getString("imagemPadrao")
                );

                String imagensConcatenadas = rs.getString("imagens");
                if (imagensConcatenadas != null && !imagensConcatenadas.isEmpty()) {
                    String[] imagens = imagensConcatenadas.split(",");
                    produto.setImagens(Arrays.asList(imagens));
                }

                produtos.add(produto);
            }
        }
        return produtos;
    }

    public Produto findProdutoById(int idProduto) throws SQLException {
        String sql = "SELECT * FROM Produto WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retorna um novo objeto Produto com os dados da linha encontrada
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("avaliacao"),
                            rs.getString("descricaoDetalhada"),
                            rs.getBigDecimal("precoProduto"),
                            rs.getInt("qtdEstoque"),
                            new ArrayList<>(),  // Inicializa a lista de imagens vazia
                            rs.getString("imagemPadrao")  // Imagem padrão
                    );
                } else {
                    System.out.println("Produto com ID " + idProduto + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Repassa a exceção para tratamento superior
        }
        return null; // Retorna null se nenhum produto for encontrado
    }

    public void alterarProduto(Produto produto) throws SQLException {
        String atualizarProdutoSQL = "UPDATE Produto SET nome = ?, avaliacao = ?, descricaoDetalhada = ?, precoProduto = ?, qtdEstoque = ?, imagemPadrao = ?, imagens = ? WHERE id = ?";

        try (PreparedStatement psProduto = connection.prepareStatement(atualizarProdutoSQL)) {
            // Atualizar dados do produto
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setBigDecimal(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.setString(6, produto.getImagemPadrao());

            // Concatena as imagens para serem armazenadas como uma string no banco de dados
            String imagensConcatenadas = produto.getImagens() != null ? String.join(",", produto.getImagens()) : "";
            psProduto.setString(7, imagensConcatenadas);

            // Define o ID do produto para atualização
            psProduto.setInt(8, produto.getId());

            // Executa a atualização no banco de dados
            psProduto.executeUpdate();
        }
    }

    public boolean StatusProduto(int idProduto, boolean novoStatus) {
        String sql = "UPDATE produto SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, novoStatus); // Define o novo status
            stmt.setInt(2, idProduto);      // Define o ID do produto
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;        // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
            return false;                   // Retorna false se houve erro
        }
    }




}
