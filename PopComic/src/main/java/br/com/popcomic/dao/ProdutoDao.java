package br.com.popcomic.dao;

import br.com.popcomic.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        String inserirProdutoSQL = "INSERT INTO Produto (nome, avaliacao, descricaoDetalhada, precoProduto, qtdEstoque, imagemPadrao, imagens) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psProduto = connection.prepareStatement(inserirProdutoSQL)) {
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setDouble(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.setString(6, produto.getImagemPadrao());

            // Concatena as imagens em uma string separada por vírgulas
            String imagensConcatenadas = String.join(",", produto.getImagens());
            psProduto.setString(7, imagensConcatenadas);

            int linhasAfetadas = psProduto.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se ao menos uma linha foi inserida
        }
    }
    public List<Produto> listarProdutos() throws SQLException {
        String listarProdutosSQL = "SELECT id, nome, avaliacao, descricaoDetalhada, precoProduto, qtdEstoque, imagemPadrao, imagens FROM Produto";

        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(listarProdutosSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Obtém os dados básicos do produto
                Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getDouble("avaliacao"),
                        rs.getString("descricaoDetalhada"),
                        rs.getDouble("precoProduto"),
                        rs.getInt("qtdEstoque"),
                        new ArrayList<>(), // Lista de imagens será preenchida a seguir
                        rs.getString("imagemPadrao") // Imagem padrão
                );

                // Obtém a string de imagens e separa em uma lista
                String imagensConcatenadas = rs.getString("imagens");
                if (imagensConcatenadas != null && !imagensConcatenadas.isEmpty()) {
                    String[] imagens = imagensConcatenadas.split(",");
                    produto.getImagens().addAll(Arrays.asList(imagens));
                }

                produtos.add(produto);
            }
        }
        return produtos;
    }

    public Produto findProdutoById(int idProduto) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retorna um novo objeto Produto com os dados da linha encontrada
                    return new Produto(
                            rs.getString("nome"),
                            rs.getDouble("avaliacao"),
                            rs.getString("descricaoDetalhada"),
                            rs.getDouble("precoProduto"),
                            rs.getInt("qtdEstoque"),
                            new ArrayList<>(),  // Inicializa a lista de imagens vazia
                            null  // Imagem padrão será adicionada separadamente
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
            psProduto.setDouble(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.setString(6, produto.getImagemPadrao());

            // Concatena as imagens para serem armazenadas como uma string no banco de dados
            String imagensConcatenadas = String.join(",", produto.getImagens());
            psProduto.setString(7, imagensConcatenadas);

            // Define o ID do produto para atualização
            psProduto.setInt(8, produto.getId());

            // Executa a atualização no banco de dados
            psProduto.executeUpdate();
        }

        System.out.println("Produto atualizado com sucesso!");
    }

    public boolean StatusProduto(int idProduto, boolean status) throws SQLException {
        String sql = "UPDATE Produto SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o novo status
            stmt.setBoolean(1, status);
            // Define o ID do produto
            stmt.setInt(2, idProduto);

            // Executa a atualização e verifica se alguma linha foi afetada
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
