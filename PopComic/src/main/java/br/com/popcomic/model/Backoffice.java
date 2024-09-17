package br.com.popcomic.model;

import br.com.popcomic.Controller.BackofficeController;
import br.com.popcomic.Controller.ProdutoController;
import br.com.popcomic.dao.ProdutoDao;
import br.com.popcomic.dao.UserDao;

import java.sql.SQLException;
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

        User user = null;  // Variável para armazenar o usuário logado

        while (user == null) {

            System.out.print("Usuário: ");
            String email = scanner.next();

            System.out.print("Senha: ");
            String senha = scanner.next();

            user = userDao.ValidarLogin(email, senha);  // Validar login e armazenar o usuário logado

            if (user != null) {
                System.out.println("Login bem-sucedido!");
                System.out.println("Bem-vindo, " + user.getNome() + "!");
            } else {
                System.out.println(" << Não foi possível identificar o usuário, tente novamente! >> ");
            }
        }

        // Menu de operações após login
        boolean continuar = true;

        while (continuar) {
            System.out.println("--------------- Área Backoffice ------------");
            System.out.println("1- Listar Produtos");
            System.out.println("2- Listar Usuários");
            System.out.println("0- Sair");
            System.out.print("Escolha (1, 2 ou 0) -> ");
            int opc = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            switch (opc) {
                case 1:
                    while (true){
                        try{
                            //Listar os produto
                            List<Produto> produtos = new ProdutoDao().listarProdutos();
                            if (produtos.isEmpty()) {
                                System.out.println("Nenhum produto encontrado.");
                            } else {
                                System.out.println("ID | Nome | Quantidade | Valor | Status");
                                System.out.println("------------------------------------------");
                                for (Produto produtoItem : produtos) {
                                    String status = produtoItem.isStatus() ? "Disponível" : "Indisponível";
                                    System.out.printf("%d | %s | %d | %.2f | %s\n", produtoItem.getId(), produtoItem.getNome(), produtoItem.getQtdEstoque(), produtoItem.getPrecoProduto(), status);
                                }
                            }

                            System.out.print("\nEntre com o ID para editar/ativar/inativar, 0 para voltar ou 'i' para incluir um produto novo: ");
                            String input = scanner.nextLine();

                            //incluir produto
                            if(input.equalsIgnoreCase("i")){
                                produtoController.incluirProduto();

                                //Voltar para tela de listagem
                            } else if (input.equals("0")) {
                                break;  // Sai do loop de listagem e volta para o menu principal

                                //Edição
                            }else{
                                try{

                                    int id = Integer.parseInt(input);
                                    Produto produto = produtoDao.findProdutoById(id);

                                    if(produto != null){
                                        produtoController.editarProduto(produto);

                                    }else{
                                        System.out.println("Produto com id " + produto.getId() + "não encontrado");
                                    }
                                } catch (Exception e) {
                                    System.out.println("ID inválido. Por favor, insira um número válido.");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao listar Produtos");
                        }
                    break;
                    }

                case 2:
                    while (true) { // Loop para permitir retornar à listagem de usuários.
                        try {
                            // Listar os usuários
                            List<User> users = userDao.listUser();
                            if (users.isEmpty()) {
                                System.out.println("Nenhum usuário encontrado.");
                            } else {
                                System.out.println("ID | Nome | CPF | Email | Status | Grupo");
                                System.out.println("------------------------------------------");
                                for (User userItem : users) {
                                    String status = userItem.isStatus() ? "Ativo" : "Inativo";
                                    System.out.printf("%d | %s | %s | %s | %s | %s\n", userItem.getIdUser(), userItem.getNome(), userItem.getCpf(), userItem.getEmail(), status, userItem.getGrupo());
                                }
                            }

                            // Apresentar as opções após a listagem
                            System.out.print("\nEntre com o ID para editar/ativar/inativar, 0 para voltar ou 'i' para incluir um novo usuário: ");
                            String input = scanner.nextLine();

                            // Incluir um novo usuário
                            if (input.equals("i")) {
                                backofficeController.incluirUsuario();

                                // Voltar para o menu principal
                            } else if (input.equals("0")) {
                                break;  // Sai do loop de listagem e volta para o menu principal

                            } else {
                                try {
                                    int id = Integer.parseInt(input);
                                    User userItem = userDao.findUserById(id);

                                    if (userItem != null) {
                                        backofficeController.editarUsuario(userItem);
                                    } else {
                                        System.out.println("Usuário com id:" + id + " não encontrado");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("ID inválido. Por favor, insira um número válido.");
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao listar usuários: " + e.getMessage());
                        }
                    }
                    break;
                }
            }
        }
    }

