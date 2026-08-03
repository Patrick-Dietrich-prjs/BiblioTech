package bibliotech.dao;

import bibliotech.model.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelo acesso a dados da entidade Livro.
 *
 * Script de criação da tabela (executar uma vez no MySQL):
 *
 * CREATE TABLE livro (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     titulo VARCHAR(150) NOT NULL,
 *     autor VARCHAR(120) NOT NULL,
 *     categoria VARCHAR(60),
 *     qtd_exemplares INT NOT NULL,
 *     qtd_disponivel INT NOT NULL
 * );
 */
public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor, categoria, qtd_exemplares, qtd_disponivel) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria());
            stmt.setInt(4, livro.getQtdExemplares());
            stmt.setInt(5, livro.getQtdDisponivel());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }
        }
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, categoria = ?, "
                + "qtd_exemplares = ?, qtd_disponivel = ? WHERE id = ?";

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria());
            stmt.setInt(4, livro.getQtdExemplares());
            stmt.setInt(5, livro.getQtdDisponivel());
            stmt.setInt(6, livro.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id = ?";

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarLivro(rs);
                }
            }
        }
        return null;
    }

    public List<Livro> listarDisponiveis() throws SQLException {
        String sql = "SELECT * FROM livro WHERE qtd_disponivel > 0 ORDER BY titulo";
        List<Livro> livros = new ArrayList<>();

        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                livros.add(montarLivro(rs));
            }
        }
        return livros;
    }

    private Livro montarLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setCategoria(rs.getString("categoria"));
        livro.setQtdExemplares(rs.getInt("qtd_exemplares"));
        livro.setQtdDisponivel(rs.getInt("qtd_disponivel"));
        return livro;
    }
    
    public List<Livro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM livro ORDER BY titulo";
        List<Livro> livros = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livros.add(montarLivro(rs));
            }
        }
        return livros;
    }
}