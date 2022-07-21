package persistencia;

import dados.Comment;
import dados.Post;
import dados.User;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PostDAO {
    private static PostDAO instance = null;
    private static CommentDAO commentDAO = null;

    private PreparedStatement selectNewId;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement select;
    private PreparedStatement selectAll;
    private PreparedStatement update;
    private PreparedStatement selectFriend;
    private PreparedStatement feed;
    private PreparedStatement selectNewIdFeed;

    private PostDAO() throws ClassNotFoundException, SQLException, SelectException{
        Connection connection = Conexao.getConexao();

        selectNewId = connection.prepareStatement("select nextval('id_post')");
        insert = connection.prepareStatement("insert into post values (?,?,?)");
        delete = connection.prepareStatement("delete from post where id_post = ?;delete from commentary where id_post = ?; delete from feed where id = ?");
        selectAll = connection.prepareStatement("select * from post");
        selectFriend = connection.prepareStatement("select * from userprofile where id_friend = ?");
        select = connection.prepareStatement("select * from post where id_friend = ?");
        update = connection.prepareStatement("update pessoa set id = ?, image = ?");
        feed = connection.prepareStatement("insert into feed values (?,?,?)");
        selectNewIdFeed = connection.prepareStatement("select nextval('id_feed')");

        commentDAO = CommentDAO.getInstance();
    }

    public List<Comment> selectAll() throws SelectException {
        List<Comment> lista = new LinkedList<Comment>();
        try {
            ResultSet rs = selectAll.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String image = rs.getString(2);
                int id_user = rs.getInt(3);

                Comment post = commentDAO.select(rs.getInt(1));
                lista.add(post);
            }
        } catch (SQLException e) {
            throw new SelectException("Nao foi possivel selecionar todos");
        }
        return lista;
    }
    public Post select(int post) throws SelectException {
        try {
            select.setInt(1, post);
            ResultSet rs = select.executeQuery();
            if(rs.next()) {
                int id = rs.getInt(1);
                String image = rs.getString(2);
                int id_user = rs.getInt(3);
                Post post1 = new Post();
                post1.setId(id);
                post1.setImage(image);
                post1.setIdUsuario(id_user);

                return post1;
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar amigo");
        }
        return null;
    }
    public void update(Post post) throws UpdateException {
        try {
            update.setInt(1, post.getId());
            update.setString(2, post.getImage());
            update.setInt(3, post.getIdUsuario());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel fazer o update do post");
        }
    }
    public static PostDAO getInstance() throws ClassNotFoundException, SQLException, SelectException {
        if (instance == null) {
            instance = new PostDAO();
        }
        return instance;
    }
    public int selectNewId() throws SelectException{
        try {
            ResultSet rs = selectNewId.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SelectException("Nao foi possivel criar um novo ID");
        }
        return 0;
    }
    public int selectNewIdFeed() throws SelectException{
        try {
            ResultSet rs = selectNewIdFeed.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SelectException("Nao foi possivel criar um novo ID");
        }
        return 0;
    }
    public void delete(Post post) throws DeleteException {
        try {
            delete.setInt(1, post.getId());
            delete.setInt(2, post.getId());
            delete.setInt(2, post.getId());
            delete.executeQuery();
        } catch (SQLException e) {
            throw new DeleteException("Nao foi possivel deletar");
        }
    }
    public void insert(Post post, User user, List<User> friends) throws InsertException, SelectException {
        try {
            post.setId(selectNewId());
            insert.setInt(1, post.getId());
            insert.setString(2, post.getImage());
            insert.setInt(3,user.getId());
            insert.executeUpdate();
            for(User friend : friends) {
                feed.setInt(1, selectNewIdFeed());
                feed.setInt(2,user.getId());
                feed.setInt(3,friend.getId());
                feed.executeUpdate();
            }
        } catch (SQLException f) {
            throw new InsertException("Nao foi possivel inserir o post");
        }
    }
}
