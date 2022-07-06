package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection conexao = null;
    private static String senha;

    private Conexao() {}

    public static void setSenha(String pass) {
        senha = pass;
    }

    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        if (conexao == null) {
            String url = "jdbc:postgres://localhost:5432/instagram";
            String usuario = "postgres";
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection(url, usuario, senha);
        }
        return conexao;
    }
}
