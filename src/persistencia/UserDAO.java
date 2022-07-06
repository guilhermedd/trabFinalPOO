package persistencia;

import dados.User;
import dados.*;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;

import java.sql.*;
import java.util.List;

public class UserDAO {
    private static UserDAO instance = null;

    private PreparedStatement selectNewID;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement follow;
    private PreparedStatement selectNewIdFriends;
    private PreparedStatement deleteFriends;
    private PreparedStatement likePost;

    public static UserDAO getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = Conexao.getConexao();
        selectNewID = conexao.prepareStatement("select nextval('id_userProfile')");
        selectNewIdFriends = conexao.prepareStatement("select nextval('id_friends')");
        insert = conexao.prepareStatement("insert into userprofile values(?,?,?,?,?)");
        select = conexao.prepareStatement("select * from userprofile where id_friend = ?");
        delete = conexao.prepareStatement("delete from userprofile where id = ?");
        deleteFriends = conexao.prepareStatement("delete from friends where followed = ?");
        update = conexao.prepareStatement("update userprofile set id = ?, username = ?, fullname = ?, bio = ?");
        follow = conexao.prepareStatement("insert into friends values(?,?,?)");
        likePost = conexao.prepareStatement("update post set likes = ?");
    }
    public void likePost(Post post) throws UpdateException {
        try {
            post.setLikes(post.getLikes() + 1);
            likePost.setInt(1, post.getLikes());
            likePost.executeQuery();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel dar like na foto");
        }
    }

    public int selectNewIdFeed() throws InsertException{
        try {
            ResultSet rs = selectNewIdFriends.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new InsertException("Nao foi possivel criar um novo ID");
        }
        return 0;
    }

    public int selectNewID() throws SelectException {
        try {
            ResultSet rs = selectNewID.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo ID da tabela User");
        }
        return 0;
    }

    public void follow(User user, User friend) throws InsertException {
        try {
            follow.setInt(1, selectNewIdFeed());
            follow.setInt(2, user.getId());
            follow.setInt(3, friend.getId());
            follow.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao seguir o usuario");
        }
    }
    public void insert(User user) throws InsertException, SelectException {
        try {
            insert.setInt(1, selectNewID());
            insert.setString(2, user.getUsername());
            insert.setString(3, user.getPassword());
            insert.setString(4, user.getFullName());
            insert.setString(5, user.getBio());

            insert.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir o usuario");
        }
    }
    public User select(int friend) throws SelectException {
        try {
            select.setInt(1, friend);
            ResultSet rs = select.executeQuery();
            if(rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getNString(2);
                String password = rs.getNString(3);
                String fullName = rs.getNString(4);
                String bio = rs.getNString(5);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setFullName(fullName);
                user.setBio(bio);
                return user;
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar amigo");
        }
        return null;
    }
    public void deleteFriends(User user) throws DeleteException {
        try {
            delete.setInt(1, user.getId());
            delete.executeQuery();
        } catch (SQLException e) {
            throw new DeleteException("Nao foi possivel deletar");
        }
    }
    public void delete(User user) throws DeleteException {
        try {
            delete.setInt(1, user.getId());
            delete.executeQuery();
            deleteFriends.setInt(1, user.getId());
            deleteFriends.executeQuery();
        } catch (SQLException e) {
            throw new DeleteException("Nao foi possivel deletar");
        }
    }
    public void update(User user) throws UpdateException {
        try {
            update.setInt(1,user.getId());
            update.setString(2, user.getUsername());
            update.setString(3, user.getPassword());
            update.setString(4, user.getFullName());
            update.setString(5, user.getBio());
            update.executeQuery();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel fazer o update");
        }
    }
}
