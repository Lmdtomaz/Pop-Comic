package br.com.popcomic.dao;

import br.com.popcomic.model.Produto;

import java.sql.*;
import java.util.ArrayList;
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
        String atualizarProdutoSQL = "UPDATE produtos SET nome = ?, avaliacao = ?, descricaoDetalhada = ?, precoProduto = ?, qtdEstoque = ? WHERE id = ?";
        String atualizarImagemSQL = "UPDATE imagens SET caminhoImagem = ?, imagemPadrao = ? WHERE produto_id = ? AND caminhoImagem = ?";

        try (PreparedStatement psProduto = connection.prepareStatement(atualizarProdutoSQL)) {
            // Atualizar dados do produto
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getAvaliacao());
            psProduto.setString(3, produto.getDescricaoDetalhada());
            psProduto.setDouble(4, produto.getPrecoProduto());
            psProduto.setInt(5, produto.getQtdEstoque());
            psProduto.executeUpdate();

            // Atualizar imagens associadas ao produto
            for (String imagem : produto.getImagens()) {
                try (PreparedStatement psImagem = connection.prepareStatement(atualizarImagemSQL)) {
                    psImagem.setString(1, imagem);
                    psImagem.setBoolean(2, imagem.equals(produto.getImagemPadrao()));
                    psImagem.setString(3, imagem);  // Referenciar a imagem pelo caminho original
                    psImagem.executeUpdate();
                }
            }

            System.out.println("Produto atualizado com sucesso!");
        }
    }

}
