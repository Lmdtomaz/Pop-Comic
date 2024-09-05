package br.com.popcomic.dao;

import br.com.popcomic.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDao {

    private Connection connection;

    public UserDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    public boolean registerUser(User user) throws SQLException {
//        String sql = "INSERT INTO users (nome, cpf, email, status, grupo, senha, repetirSenha) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, user.getNome());
//            stmt.setString(2, user.getCpf());
//            stmt.setString(3, user.getEmail());
//            stmt.setBoolean(4, user.isStatus());
//            stmt.setString(5, user.getGrupo());
//            // Criptografar a senha antes de armazenar
//            String hashedSenha = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());
//            stmt.setString(6, hashedSenha);
//            stmt.setString(7, hashedSenha); // Se você estiver armazenando repetirSenha, faça o mesmo
//
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


    public boolean UpdateUser(User user) throws SQLException {///ALTERAR
        String sql = "UPDATE users SET nome = ?, cpf = ?, email = ?, status = ?, grupo = ?, senha = ? WHERE idUser = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getCpf());
            stmt.setString(3, user.getEmail());
            stmt.setBoolean(4, user.isStatus());
            stmt.setString(5, user.getGrupo());
            stmt.setString(6, user.getSenha());
            stmt.setInt(7, user.getIdUser());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean StatusUsuario(int idUser, boolean status) throws SQLException { // STATUS USUARIO
        String sql = "UPDATE users SET status = ? WHERE idUser = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, idUser);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUserById(int idUser) throws SQLException {
        String sql = "SELECT * FROM users WHERE idUser = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("idUser"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getBoolean("status"),
                            rs.getString("grupo"),
                            rs.getString("senha"),
                            rs.getString("repetirSenha")
                    );
                } else {
                    System.out.println("Usuário com ID " + idUser + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Repassa a exceção para que possa ser tratada mais acima, se necessário
        }
        return null; // Se nenhum usuário for encontrado
    }


    public User ValidarLogin(String email, String senha) {
        String SQL = "SELECT * FROM users WHERE EMAIL = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedSenha = resultSet.getString("SENHA");
                Boolean status = resultSet.getBoolean("STATUS");
                String grupo = resultSet.getString("GRUPO");
                String nome = resultSet.getString("NOME");

                // Verificar se o hash da senha está correto
                if (BCrypt.checkpw(senha, hashedSenha) && status && !grupo.equalsIgnoreCase("CLIENTE")) {
                    User user = new User();
                    user.setIdUser(resultSet.getInt("IDUSER"));
                    user.setNome(nome);
                    user.setEmail(email);
                    user.setStatus(status);
                    user.setGrupo(grupo);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Alterar senha
    public boolean alterarSenha(int idUser, String novaSenha, String repetirSenha) throws SQLException {


        // Verifica se a nova senha e a senha repetida são iguais
        if (!novaSenha.equals(repetirSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        // Criptografar a nova senha
        String hashedSenha = BCrypt.hashpw(novaSenha, BCrypt.gensalt());

        String sql = "UPDATE users SET SENHA = ? WHERE IDUSER = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hashedSenha);
            stmt.setInt(2, idUser);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean AlterarUsuario(int idUser, String novoNome, String novoCpf, String novoGrupo) throws SQLException {
        // Verifica se o novo nome não está vazio ou nulo
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar vazio.");
        }

        // Verifica se o novo CPF tem exatamente 11 dígitos
        if (novoCpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve ter exatamente 11 dígitos.");
        }

        // Verifica se o novo grupo é "administrador" ou "estoquista"
        if (!novoGrupo.equalsIgnoreCase("administrador") && !novoGrupo.equalsIgnoreCase("estoquista")) {
            throw new IllegalArgumentException("O grupo deve ser 'administrador' ou 'estoquista'.");
        }

        String sql = "UPDATE users SET NOME = ?, CPF = ?, GRUPO = ? WHERE IDUSER = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoCpf);
            stmt.setString(3, novoGrupo);
            stmt.setInt(4, idUser);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> listUser() throws SQLException{
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getBoolean("status"),
                        rs.getString("grupo"),
                        rs.getString("senha"),
                        rs.getString("repetirSenha")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean incluirUsuario(User user) {
        var sql = """
            INSERT INTO users (nome, cpf, email, status, grupo, senha, repetirSenha) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;


        Objects.requireNonNull(user, "User cannot be null");

        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getCpf());
            stmt.setString(3, user.getEmail());
            stmt.setBoolean(4, user.isStatus());
            stmt.setString(5, user.getGrupo());
            var hashedSenha = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());
            stmt.setString(6, hashedSenha);
            stmt.setString(7, hashedSenha);
            var rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}




