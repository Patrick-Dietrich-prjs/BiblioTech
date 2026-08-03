package bibliotech.dao;

import bibliotech.model.TipoUsuario;
import bibliotech.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelo acesso a dados da entidade Usuario.
 *
 * Etapa atual do projeto: apenas inserir() e listar() estão implementados.
 * Os demais métodos serão implementados em uma próxima entrega.
 *
 * Script de criação da tabela:
 *
 * CREATE TABLE usuario (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     nome VARCHAR(150) NOT NULL,
 *     tipo VARCHAR(20) NOT NULL,
 *     matricula VARCHAR(30),
 *     contato VARCHAR(100)
 * );
 */
public class UsuarioDAO {

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, tipo, matricula, contato) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTipo().name());
            stmt.setString(3, usuario.getMatricula());
            stmt.setString(4, usuario.getContato());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT * FROM usuario ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
                usuario.setMatricula(rs.getString("matricula"));
                usuario.setContato(rs.getString("contato"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, tipo = ?, matricula = ?, contato = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTipo().name());
            stmt.setString(3, usuario.getMatricula());
            stmt.setString(4, usuario.getContato());
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
                    u.setMatricula(rs.getString("matricula"));
                    u.setContato(rs.getString("contato"));
                    return u;
                }
            }
        }
        return null;
    }
}