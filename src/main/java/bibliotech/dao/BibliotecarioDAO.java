package bibliotech.dao;

import bibliotech.model.Bibliotecario;
import java.sql.*;

public class BibliotecarioDAO {

    public Bibliotecario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM bibliotecario WHERE login = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bibliotecario b = new Bibliotecario();
                    b.setId(rs.getInt("id"));
                    b.setLogin(rs.getString("login"));
                    b.setSenha(rs.getString("senha")); // já está em MD5
                    return b;
                }
            }
        }
        return null;
    }
}