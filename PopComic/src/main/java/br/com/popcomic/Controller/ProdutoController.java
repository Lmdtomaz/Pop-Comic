package br.com.popcomic.Controller;

import br.com.popcomic.dao.ProdutoDao;
import br.com.popcomic.model.Produto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class ProdutoController {

    private ProdutoDao produtoDao;
    static Scanner scanner = new Scanner(System.in);

    public ProdutoController(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public void incluirProduto() {
        System.out.println("------ Inclusão de Novo Produto ------");

        // Coletar as informações do produto
        System.out.print("Nome => ");
        String nome = scanner.nextLine();

        System.out.print("Preço => ");
        BigDecimal preco;
        try {
            preco = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido. Tente novamente.");
            return;
        }

        System.out.print("Quantidade em Estoque => ");
        int quantidadeEstoque;
        try {
            quantidadeEstoque = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida. Tente novamente.");
            return;
        }

        System.out.print("Descrição => ");
        String descricao = scanner.nextLine();

        System.out.print("Avaliação (0 a 5) => ");
        double avaliacao;
        try {
            avaliacao = Double.parseDouble(scanner.nextLine());
            if (avaliacao < 0 || avaliacao > 5) {
                System.out.println("Avaliação deve estar entre 0 e 5. Tente novamente.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Avaliação inválida. Tente novamente.");
            return;
        }

        // Mostrar um resumo e pedir confirmação para salvar
        System.out.println("-------------------------------------------------------------------");
        System.out.print("Salvar (Y/N) => ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("Y")) {
            try {
                // Criar um objeto Produto (observação: o campo descrição agora é descriçãoDetalhada)
                Produto produto = new Produto(nome, preco, quantidadeEstoque, descricao, avaliacao);

                // Cadastrar o produto usando o método da DAO
                boolean sucesso = produtoDao.cadastrarProduto(produto);

                if (sucesso) {
                    System.out.println("Produto cadastrado com sucesso.");
                    incluirImagem(produto);
                } else {
                    System.out.println("Falha ao cadastrar o produto.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
            }
        } else {
            System.out.println("Inclusão de produto cancelada.");
        }
    }

    public void incluirImagem(Produto prod) {
        System.out.println("Nome do arquivo:");
        String nomeArquivo = scanner.nextLine();

        System.out.println("Diretório de origem da imagem:");
        String diretorioImagem = scanner.nextLine();

        System.out.println("Principal (S/N):");
        String principal = scanner.nextLine();
        boolean isPrincipal = principal.equalsIgnoreCase("S");

        System.out.println("------------------------------------");
        System.out.println("Opções:");
        System.out.println("1) Salvar e incluir + 1 imagem");
        System.out.println("2) Salvar e finalizar");
        System.out.println("3) Não salvar e finalizar");
        System.out.print("Entre com a opção (1, 2, 3) => ");

        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                // Adicionar imagem e continuar incluindo mais imagens
                adicionarImagem(nomeArquivo, diretorioImagem, isPrincipal);
                incluirImagem(prod); // Chama o método recursivamente para adicionar mais imagens
                break;

            case "2":
                // Salvar e finalizar
                System.out.println("Produto salvo. Retornando para a listagem de produtos!");
                return;

            case "3":
                // Não salvar e finalizar
                System.out.println("Produto não salvo. Retornando para a listagem de produtos!");
                return;

            default:
                System.out.println("Opção inválida. Tente novamente.");
                incluirImagem(prod); // Chama o método novamente para selecionar uma opção válida
        }
    }

    private void adicionarImagem(String nomeArquivo, String diretorioImagem, boolean principal) {
        // Implementar a lógica para adicionar a imagem
        // Exemplo:
        System.out.println("Adicionando imagem:");
        System.out.println("Nome do arquivo: " + nomeArquivo);
        System.out.println("Diretório da imagem: " + diretorioImagem);
        System.out.println("Principal: " + (principal ? "Sim" : "Não"));

        // Aqui você deve adicionar o código para salvar a imagem
    }

    public void editarProduto(Produto produto) throws SQLException {
        while (true) {
            System.out.println("\nId: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preco: " + produto.getPrecoProduto());
            System.out.println("Quantidade em estoque: " + produto.getQtdEstoque());
            System.out.println("Avaliação: " + produto.getAvaliacao());
            System.out.println("Status: " + (produto.isStatus() ? "ativo" : "inativo"));
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Opções");
            System.out.println("1) Alterar Produto");
            System.out.println("2) Lista/Alterar imagens do produto");
            System.out.println("3) Ativar/Desativar produto");
            System.out.println("4) Voltar para lista de produtos");
            System.out.print("Entre com a opção (1,2,3,4) => ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1": // Alterar produto
                    System.out.println("Alterar nome do produto:");
                    produto.setNome(scanner.nextLine());

                    System.out.println("Alterar preço:");
                    produto.setPrecoProduto (BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));



                    System.out.println("Alterar Estoque:");
                    produto.setQtdEstoque(Integer.parseInt(scanner.nextLine()));

                    System.out.println("Alterar descrição detalhada:");
                    produto.setDescricaoDetalhada(scanner.nextLine());

                    System.out.println("Alterar avaliação:");
                    produto.setAvaliacao(Double.parseDouble(scanner.nextLine()));

                    // Chama o método para atualizar o produto no banco
                    produtoDao.alterarProduto(produto);
                    break;

                case "2": // Lista/Alterar imagens do produto
                    System.out.println("Imagens atuais: " + produto.getImagens());
                    System.out.println("Alterar imagens (digite as novas imagens separadas por vírgula):");
                    String novasImagens = scanner.nextLine();
                    produto.setImagens(Arrays.asList(novasImagens.split(",")));

                    System.out.println("Digite o caminho da imagem padrão:");
                    produto.setImagemPadrao(scanner.nextLine());

                    // Atualiza o produto com as novas imagens
                    produtoDao.alterarProduto(produto);
                    break;

                case "3": // Ativar/Desativar produto
                    boolean novoStatusProduto = !produto.isStatus();

                    boolean sucesso = produtoDao.StatusProduto(produto.getId(), novoStatusProduto);
                    if (sucesso) {
                        System.out.println("Produto " + (novoStatusProduto ? "ativado" : "desativado") + " com sucesso!");
                        produto.setStatus(novoStatusProduto);
                    } else {
                        System.out.println("Erro ao " + (novoStatusProduto ? "ativar" : "desativar") + " o produto.");
                    }
                    break;

                case "4": // Voltar para a lista de produtos
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
