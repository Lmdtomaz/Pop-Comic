package br.com.popcomic.dao;

import br.com.popcomic.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDao {

    private Connection connection;

    public UserDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (nome, cpf, email, status, grupo, senha, repetirSenha) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getCpf());
            stmt.setString(3, user.getEmail());
            stmt.setBoolean(4, user.isStatus());
            stmt.setString(5, user.getGrupo());
            // Criptografar a senha antes de armazenar
            String hashedSenha = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());
            stmt.setString(6, hashedSenha);
            stmt.setString(7, hashedSenha); // Se você estiver armazenando repetirSenha, faça o mesmo

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


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

    public User getUserById(int idUser) throws SQLException {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

}



