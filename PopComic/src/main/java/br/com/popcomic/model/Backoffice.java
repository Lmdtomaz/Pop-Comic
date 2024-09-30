package br.com.popcomic.model;

import br.com.popcomic.Controller.BackofficeController;
import br.com.popcomic.dao.ProdutoDao;
import br.com.popcomic.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Backoffice {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        // Login
        UserDao userDao = new UserDao(); // Instanciar UserDao uma vez aqui
        BackofficeController backofficeController = new BackofficeController(userDao);


        while (true) {

            System.out.print("Usuário: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

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
            System.out.print("Escolha (1 ou 2) -> ");
            int opc = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            switch (opc) {
                case 1:
                    // Inserir metodo para listar produtos.
                break;

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

                            //inclui um nobo usuario
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
            }
        }

        //Produto


    }
//    ProdutoDao produtoDao = new ProdutoDao();

}


