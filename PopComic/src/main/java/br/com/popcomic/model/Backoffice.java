package br.com.popcomic.model;

import br.com.popcomic.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Backoffice {

    public static void main(String[] args) {

        String url = "jdbc:h2:~/test";
        String usuario = "sa";
        String password = "sa";
        Scanner scanner = new Scanner(System.in);

        // Login
        UserDao userDao = new UserDao(); // Instanciar UserDao uma vez aqui
        User user = null;

        while (user == null) {
            System.out.print("Usuário (e-mail): ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            user = userDao.ValidarLogin(email, senha);

            if (user != null) {
                System.out.println("Login bem-sucedido!");
                System.out.println("Bem-vindo, " + user.getNome());
            } else {
                System.out.println("<< Não foi possível identificar o usuário, tente novamente! >>");
            }
        }

        // Menu de operações após login
        while (true) {
            System.out.println("--------------- Área Backoffice ------------");
            System.out.println("1- Listar Produtos");
            System.out.println("2- Listar Usuário");
            System.out.print("Escolha (1 ou 2) -> ");
            int vlr1 = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            switch (vlr1) {
                case 1:
                    // Implementar a lógica para listar produtos
                    break;
                case 2:
                    listarUsuarios(userDao, scanner);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void listarUsuarios(UserDao userDao, Scanner scanner) {
        String url = "jdbc:h2:~/test";
        String usuario = "sa";
        String password = "sa";

        try (Connection connection = DriverManager.getConnection(url, usuario, password);
             java.sql.Statement stmt = connection.createStatement()) {

            String sql = "SELECT * FROM users";
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idUser = rs.getInt("IDUSER");
                String nome = rs.getString("NOME");
                String cpf = rs.getString("CPF");
                String email = rs.getString("EMAIL");
                boolean status = rs.getBoolean("STATUS");
                String grupo = rs.getString("GRUPO");

                System.out.println(" |ID: " + idUser + ", | Nome: | " + nome + "| CPF: |" + cpf +
                        "| Email: |" + email + "| Status: " + (status ? "Ativo" : "Inativo") +
                        "| Grupo " + grupo);
            }

            System.out.println();
            System.out.println("------- O que deseja fazer? --------");
            System.out.println("1 - Editar (Editar/Ativar/Inativar)");
            System.out.println("2 - Excluir");
            System.out.println("3 - Incluir");
            System.out.print("Escolha (1, 2 ou 3) -> ");
            int vlr2 = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            switch (vlr2) {
                case 1:
                    editarUsuario(userDao, scanner);
                    break;
                case 2:
                    // Implementar a lógica para excluir um usuário se necessário
                    break;
                case 3:
                    // Implementar a lógica para incluir um usuário se necessário
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editarUsuario(UserDao userDao, Scanner scanner) {
        try {
            System.out.print("Digite o ID do usuário que deseja atualizar: ");
            int idUser = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline após o int

            User user = userDao.getUserById(idUser);

            if (user != null) {
                System.out.println("Escolha o campo que deseja atualizar:");
                System.out.println("1. Nome");
                System.out.println("2. CPF");
                System.out.println("3. Status");
                System.out.println("4. Grupo");
                System.out.println("5. Senha");
                System.out.print("Digite o número da opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir o newline após o int

                boolean atualizado = false;

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o novo nome: ");
                        String novoNome = scanner.nextLine();
                        atualizado = userDao.alterarNome(idUser, novoNome);
                        break;
                    case 2:
                        System.out.print("Digite o novo CPF: ");
                        String novoCpf = scanner.nextLine();
                        atualizado = userDao.alterarCpf(idUser, novoCpf);
                        break;
                    case 3:
                        System.out.print("Deseja " + (user.isStatus() ? "desativar" : "ativar") + " o usuário? (Y/N): ");
                        String escolha = scanner.nextLine();
                        if ("Y".equalsIgnoreCase(escolha)) {
                            user.setStatus(!user.isStatus());
                            atualizado = userDao.StatusUsuario(idUser, user.isStatus());
                        }
                        break;
                    case 4:
                        System.out.print("Digite o novo grupo (administrador ou estoquista): ");
                        String novoGrupo = scanner.nextLine();
                        atualizado = userDao.alterarGrupo(idUser, novoGrupo);
                        break;
                    case 5:
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = scanner.nextLine();
                        System.out.print("Repita a nova senha: ");
                        String repetirSenha = scanner.nextLine();
                        atualizado = userDao.alterarSenha(idUser, novaSenha, repetirSenha);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }

                if (atualizado) {
                    System.out.println("Usuário atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar usuário.");
                }

            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
