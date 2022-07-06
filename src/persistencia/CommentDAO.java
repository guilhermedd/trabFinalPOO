package persistencia;

import exceptions.InsertException;
import exceptions.SelectException;

import dados.Comment;
import exceptions.UpdateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentDAO {
    private static CommentDAO instance = null;

    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement update;
    //    Nao pode deletar o post!

    public static CommentDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }
    private CommentDAO() throws ClassNotFoundException, SQLException{
        Connection conexao = Conexao.getConexao();
        insert = conexao.prepareStatement("insert into commentary values(?,?)");
        select = conexao.prepareStatement("select * from commentary where id_post = ?");
        update = conexao.prepareStatement("update endereco set reply = ?, id_post = ?");
    }

    public void insert(Comment comentario) throws InsertException{
        try {
            insert.setString(1,comentario.getCom());
            insert.setInt(2, comentario.getIdPost());
            insert.executeQuery();
        } catch (SQLException e) {
            throw new InsertException("Nao foi possivel inserir o comentario");
        }
    }
    public Comment select(int post) throws SelectException{
        try {
            select.setInt(1, post);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String reply = rs.getNString(2);
                Comment comment = new Comment();
                comment.setCom(reply);
                comment.setIdPost(id);
                return comment;
            }
        } catch (SQLException e) {
            throw new SelectException("Nao foi possivel encontrar o comentario");
        }
        return null;
    }
    public void update(Comment comment) throws UpdateException{
        try {
            update.setString(1, comment.getCom());
            update.setInt(2, comment.getIdPost());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel fazer o update do comentario");
        }
    }
}
