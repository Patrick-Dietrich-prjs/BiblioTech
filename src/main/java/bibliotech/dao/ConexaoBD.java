package bibliotech.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária responsável por abrir a conexão JDBC com o banco MySQL.
 *
 * Ajuste URL, usuário e senha conforme o ambiente local antes de executar.
 * É necessário adicionar o driver MySQL Connector/J às bibliotecas do projeto
 * (Properties > Libraries, no NetBeans).
 */
public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotech?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    private ConexaoBD() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}