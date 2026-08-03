package bibliotech.dao;

import bibliotech.model.Emprestimo;
import bibliotech.model.Livro;
import bibliotech.model.StatusEmprestimo;
import bibliotech.model.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelo acesso a dados da entidade Emprestimo.
 *
 * Etapa atual do projeto: apenas a estrutura da classe foi criada.
 * A implementação (registro de empréstimo, devolução e consultas) fica
 * prevista para uma próxima entrega, conforme os requisitos RF03, RF04,
 * RF06 e RF07 do documento de projeto.
 *
 * Script de criação da tabela:
 *
 * CREATE TABLE emprestimo (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     livro_id INT NOT NULL,
 *     usuario_id INT NOT NULL,
 *     data_retirada DATE NOT NULL,
 *     data_prevista_devolucao DATE NOT NULL,
 *     data_efetiva_devolucao DATE,
 *     status VARCHAR(20) NOT NULL,
 *     FOREIGN KEY (livro_id) REFERENCES livro(id),
 *     FOREIGN KEY (usuario_id) REFERENCES usuario(id)
 * );
 */
public class EmprestimoDAO {

    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, data_retirada, data_prevista_devolucao, status) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, emprestimo.getLivro().getId());
            stmt.setInt(2, emprestimo.getUsuario().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataRetirada()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataPrevistaDevolucao()));
            stmt.setString(5, StatusEmprestimo.ATIVO.name());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    emprestimo.setId(rs.getInt(1));
                }
            }
        }
        
        Livro livro = emprestimo.getLivro();
        livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
        new LivroDAO().atualizar(livro);
    }

    public void registrarDevolucao(int idEmprestimo) throws SQLException {
        Emprestimo emp = buscarPorId(idEmprestimo);
        if (emp == null) return;

        String sql = "UPDATE emprestimo SET data_efetiva_devolucao = ?, status = ? WHERE id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setString(2, StatusEmprestimo.DEVOLVIDO.name());
            stmt.setInt(3, idEmprestimo);
            stmt.executeUpdate();
        }
        
        Livro livro = emp.getLivro();
        livro.setQtdDisponivel(livro.getQtdDisponivel() + 1);
        new LivroDAO().atualizar(livro);
    }

    public List<Emprestimo> listarAbertos() throws SQLException {
        String sql = "SELECT e.*, l.titulo, l.autor, l.categoria, l.qtd_exemplares, l.qtd_disponivel, "
                   + "u.nome, u.tipo, u.matricula, u.contato "
                   + "FROM emprestimo e "
                   + "JOIN livro l ON e.livro_id = l.id "
                   + "JOIN usuario u ON e.usuario_id = u.id "
                   + "WHERE e.status = 'ATIVO' ORDER BY e.data_prevista_devolucao";
        return montarLista(sql);
    }

    public List<Emprestimo> listarAtrasados() throws SQLException {
        String sql = "SELECT e.*, l.titulo, l.autor, l.categoria, l.qtd_exemplares, l.qtd_disponivel, "
                   + "u.nome, u.tipo, u.matricula, u.contato "
                   + "FROM emprestimo e "
                   + "JOIN livro l ON e.livro_id = l.id "
                   + "JOIN usuario u ON e.usuario_id = u.id "
                   + "WHERE e.status = 'ATIVO' AND e.data_prevista_devolucao < CURDATE() "
                   + "ORDER BY e.data_prevista_devolucao";
        return montarLista(sql);
    }

    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT e.*, l.titulo, l.autor, l.categoria, l.qtd_exemplares, l.qtd_disponivel, "
                   + "u.nome, u.tipo, u.matricula, u.contato "
                   + "FROM emprestimo e "
                   + "JOIN livro l ON e.livro_id = l.id "
                   + "JOIN usuario u ON e.usuario_id = u.id "
                   + "WHERE e.id = ?";
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarEmprestimo(rs);
                }
            }
        }
        return null;
    }

    private List<Emprestimo> montarLista(String sql) throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection con = ConexaoBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(montarEmprestimo(rs));
            }
        }
        return lista;
    }

    private Emprestimo montarEmprestimo(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("livro_id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setCategoria(rs.getString("categoria"));
        livro.setQtdExemplares(rs.getInt("qtd_exemplares"));
        livro.setQtdDisponivel(rs.getInt("qtd_disponivel"));

        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("usuario_id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setTipo(bibliotech.model.TipoUsuario.valueOf(rs.getString("tipo")));
        usuario.setMatricula(rs.getString("matricula"));
        usuario.setContato(rs.getString("contato"));

        Emprestimo emp = new Emprestimo();
        emp.setId(rs.getInt("id"));
        emp.setLivro(livro);
        emp.setUsuario(usuario);
        emp.setDataRetirada(rs.getDate("data_retirada").toLocalDate());
        emp.setDataPrevistaDevolucao(rs.getDate("data_prevista_devolucao").toLocalDate());
        Date efetiva = rs.getDate("data_efetiva_devolucao");
        if (efetiva != null) {
            emp.setDataEfetivaDevolucao(efetiva.toLocalDate());
        }
        emp.setStatus(StatusEmprestimo.valueOf(rs.getString("status")));
        return emp;
    }
}