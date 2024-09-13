package br.com.popcomic.Controller;

import br.com.popcomic.dao.ProdutoDao;
import br.com.popcomic.model.Produto;

import java.sql.SQLException;
import java.util.Scanner;

public class ProdutoController {

    private ProdutoDao produtoDao;
    static Scanner scanner = new Scanner(System.in);

    public ProdutoController(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public void editarProduto(Produto produto) throws SQLException {

        while (true) {
            System.out.println("\nId: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preco: " + produto.getPrecoProduto());
            System.out.println("Descricao: " + produto.getDescricaoDetalhada());
            System.out.println("Quantidade: " + produto.getQtdEstoque());
            System.out.println("Imagem principal " + produto.getImagemPadrao());
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Opções");
            System.out.println("1) Alterar Produto");
            System.out.println("2) Voltar para Listar Produtos");
            System.out.print("Entre com a opção (1,2,3,4) => ");

            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1": //alterar produto

                    break;


                case "2": // Alterar senha

                    break;


            }
        }
    }
}
