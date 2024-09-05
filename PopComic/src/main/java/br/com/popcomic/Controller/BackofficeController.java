package br.com.popcomic.Controller;

import br.com.popcomic.dao.UserDao;
import br.com.popcomic.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BackofficeController {

        private UserDao userDao;
        private Scanner scanner;

        public BackofficeController(UserDao userDao) throws SQLException {
            this.userDao = userDao;
            this.scanner = new Scanner(System.in);
        }




        // Metodo de inclusão de usuário
        public void incluirUsuario() {
            System.out.println("----- Inclusão de Novo Usuário -----");

            // Coletar as informações do usuário
            System.out.print("Nome => ");
            String nome = scanner.nextLine();

            System.out.print("Cpf => ");
            String cpf = scanner.nextLine();

            System.out.print("E-mail => ");
            String email = scanner.nextLine();

            System.out.print("Grupo (Administrador/Estoquista) => ");
            String grupo = scanner.nextLine();

            System.out.print("Senha => ");
            String senha = scanner.nextLine();

            System.out.print("Repetir Senha => ");
            String repetirSenha = scanner.nextLine();

            // Verificar se as senhas coincidem
            if (!senha.equals(repetirSenha)) {
                System.out.println("As senhas não correspondem. Tente novamente.");
                return;
            }

            // Mostrar um resumo e pedir confirmação para salvar
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Salvar (Y/N) => ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("Y")) {
                // Criar o objeto User
                User user = new User( nome, cpf, email, true, grupo, senha, repetirSenha);

                // Registrar o usuário no banco de dados usando incluirUsuario()
                boolean sucesso = userDao.incluirUsuario(user);

                if (sucesso) {
                    System.out.println("Usuário registrado com sucesso!");
                } else {
                    System.out.println("Erro ao registrar o usuário. Tente novamente.");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        }



    public void editarUsuario(User user) throws SQLException {

        while (true) {
            System.out.println("\nId: " + user.getIdUser());
            System.out.println("Nome: " + user.getNome());
            System.out.println("Cpf: " + user.getCpf());
            System.out.println("E-mail: " + user.getEmail());
            System.out.println("Status: " + (user.isStatus() ? "ativo" : "inativo"));
            System.out.println("Grupo: " + user.getGrupo());
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Opções");
            System.out.println("1) Alterar usuário");
            System.out.println("2) Alterar senha");
            System.out.println("3) Ativar/Desativar");
            System.out.println("4) Voltar para Listar Usuário");
            System.out.print("Entre com a opção (1,2,3,4) => ");

            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1": //Alterar usuario
                    System.out.println("Digite o novo Nome");
                    String novoNome = scanner.nextLine();
                    System.out.println("Novo CPf:");
                    String novoCPF = scanner.nextLine();
                    System.out.println("Novo Grupo");
                    String novoGrupo = scanner.nextLine();
                    boolean nomeAltarado = userDao.AlterarUsuario(user.getIdUser(), novoNome, novoCPF, novoGrupo);
                    break;


                case "2": // Alterar senha
                    System.out.print("Digite a nova senha: ");
                    String novaSenha = scanner.nextLine();
                    System.out.print("Repita a nova senha: ");
                    String repetirSenha = scanner.nextLine();
                    boolean senhaAlterada = userDao.alterarSenha(user.getIdUser(), novaSenha, repetirSenha);
                    if (senhaAlterada) {
                        System.out.println("Senha alterada com sucesso!");
                    } else {
                        System.out.println("Erro ao alterar a senha.");
                    }
                    break;

                case "3": // Ativar/Desativar usuário

                    boolean novoStatus = !user.isStatus(); // Se estiver ativo, desativa, e vice-versa

                    boolean sucesso = userDao.StatusUsuario(user.getIdUser(), novoStatus);
                    if (sucesso) {
                        System.out.println("Usuário " + (novoStatus ? "ativado" : "desativado") + " com sucesso!");
                        user.setStatus(novoStatus);
                    } else {
                        System.out.println("Erro ao alterar o status do usuário.");
                    }
                    break;

                case "4": // Voltar para a listagem de usuários
                    System.out.println("Voltando para a listagem de usuários...");
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                break;
            }
        }
    }
}


