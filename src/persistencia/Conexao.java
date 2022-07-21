package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection conexao = null;
    private static String senha;

    public Conexao() {}

    public static void setSenha(String pass) {
        senha = "udesc";
    }

    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        if (conexao == null) {
            try{
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/postgres";
                String usuario = "postgres";
                String password = "udesc";
                conexao = DriverManager.getConnection(url, usuario, password);
            } catch (ClassNotFoundException e) {
                System.out.println("Nao foi possivel encontrar o driver");
            }
            catch (SQLException e) {
                System.out.println("Nao foi possivel se conectar com o sistema");
            }
        }
        return conexao;
    }
}
