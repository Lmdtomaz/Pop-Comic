package br.com.popcomic.model;

import br.com.popcomic.Controller.BackofficeController;
import br.com.popcomic.Controller.ProdutoController;
import br.com.popcomic.dao.ProdutoDao;
import br.com.popcomic.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Backoffice {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        // Login
        UserDao userDao = new UserDao(); // Instanciar UserDao uma vez aqui
        ProdutoDao produtoDao = new ProdutoDao();
        BackofficeController backofficeController = new BackofficeController(userDao);
        ProdutoController produtoController = new ProdutoController(produtoDao);

        while (true) {

            System.out.print("Usuário: ");
            String email = scanner.next();

            System.out.print("Senha: ");
            String senha = scanner.next();
t
            userDao.ValidarLogin(email, senha);
            User user = new User();

            if (userDao != null) {
                System.out.println("Login bem-sucedido!");
                System.out.println("Bem-vindo, " + user.getNome() + "!");
                break;
            } else {
                System.out.println(" << Não foi possivel identificar o usuário, tente novamente! >> ");
            }

        }

        // Menu de operações após login

        //<<<<<<<<<<<<Logica>>>>>>>>>>>>>>

        while (true) {
            System.out.println("--------------- Área Backoffice ------------");
            System.out.println("1- Listar Produtos");
            System.out.println("2- Listar Usuários");
            System.out.println("3- Cadastrar produto");
            System.out.print("Escolha (1 ou 2) -> ");
            int opc = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            switch (opc) {
                case 1:

                    while (true) {
                        try {
                            List<Produto> produtos = produtoDao.listarProdutos();

                            if (produtos.isEmpty()) {
                                System.out.println("Nao ha produtos");
                            } else {
                                // Lógica de listagem de produtos
                                for (Produto produto : produtos) {
                                    System.out.println(produto.getNome() + " - R$ " + produto.getPrecoProduto());
                                }
                            }

                            System.out.println("Coloque o id do produto para altera-lo");
                            int choice1 = scanner.nextInt();

                            try {
                                Produto produto = produtoDao.findProdutoById(choice1);

                                if (produto != null) {
                                    ProdutoController. (produto);// Codigo de alterar produto
                                } else {
                                    System.out.println("Produto com id:" + choice1 + "nao encontrado");
                                }


                            } catch (NumberFormatException e) {
                                System.out.println("ID inválido. Por favor, insira um número válido.");
                            }
                            break;
                        }
                    }

                case 2:
                    while (true) { // Loop para permitir retornar à listagem de usuários
                        try {
                            // Listar os usuários
                            List<User> users = userDao.listUser();
                            if (users.isEmpty()) {
                                System.out.println("Nenhum usuário encontrado.");
                            } else {
                                System.out.println("ID | Nome | CPF | Email | Status | Grupo");
                                System.out.println("------------------------------------------");
                                for (User user : users) {
                                    String status = user.isStatus() ? "Ativo" : "Inativo";
                                    System.out.printf("%d | %s | %s | %s | %s | %s\n", user.getIdUser(), user.getNome(), user.getCpf(), user.getEmail(), status, user.getGrupo());
                                }
                            }

                            // Apresentar as opções após a listagem
                            System.out.print("\nEntre com o ID para editar/ativar/inativar, 0 para voltar ou 'i' para incluir um novo usuário: ");
                            String input = scanner.nextLine();

                            //inclui um novo usuario
                            if (input.equals("i")) {
                                backofficeController.incluirUsuario();

                                //volta para o menu principal
                            } else {
                                if (input.equals("0")) {

                                    break;  // Sai do loop de listagem e volta para o menu principal

                                    //edita o usuario
                                } else {
                                    try {
                                        int id = Integer.parseInt(input);
                                        User user = userDao.findUserById(id);

                                        if (user != null) {
                                            backofficeController.editarUsuario(user);
                                        } else {
                                            System.out.println("Usuario com id:" + id + "nao encontrado");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("ID inválido. Por favor, insira um número válido.");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao listar usuários: " + e.getMessage());
                        }
                    }
                break;
                case 3:
                    System.out.print("Nome do Produto: ");
                    String nome = scanner.next();

                    System.out.print("Avaliação do Produto: ");
                    double avaliacao = scanner.nextDouble();


                    System.out.print("Descrição Detalhada: ");
                    String descricao = scanner.next();

                    System.out.print("Preço do Produto: ");
                    double preco = scanner.nextDouble();


                    System.out.print("Quantidade em Estoque: ");
                    int qtdEstoque = scanner.nextInt();

                    System.out.print("Imagem Padrão do Produto: ");
                    String imagemPadrao = scanner.next();

                    List<String> imagens = new ArrayList<>();
                    imagens.add(imagemPadrao);

                    Produto produto = new Produto(nome, avaliacao, descricao, preco, qtdEstoque, , imagemPadrao);

                    produtoDao.cadastrarProduto(produto);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
            }
        }

        //Produto


    }


}


