package persistencia;

import dados.User;
import dados.*;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;

import java.sql.*;
import java.util.ArrayList;
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
    private PreparedStatement selectProfile;
    private PreparedStatement search;
    private PreparedStatement searchFriend;

    public static UserDAO getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = Conexao.getConexao();
        selectNewID = conexao.prepareStatement("select nextval('id_user')");
        insert = conexao.prepareStatement("insert into userprofile values(?,?,?,?,?)");
        search = conexao.prepareStatement("select * from userprofile where username = ?");
        searchFriend = conexao.prepareStatement("select * from friends where follower = ? or followed = ?");
        select = conexao.prepareStatement("select * from userprofile where id = ?");
        selectProfile = conexao.prepareStatement("select * from userprofile where username = ? and password = ?");
        selectNewIdFriends = conexao.prepareStatement("select nextval('id_friends')");
        delete = conexao.prepareStatement("delete from userprofile where id = ?");
        deleteFriends = conexao.prepareStatement("delete from friends where followed = ?");
        update = conexao.prepareStatement("update userprofile username = ?, fullname = ?, bio = ? where id = ?");
        follow = conexao.prepareStatement("insert into friends values(?,?,?)");
        likePost = conexao.prepareStatement("update post set likes = ? where id = ?");
    }
    public void likePost(Post post) throws UpdateException {
        try {
            post.setLikes(post.getLikes() + 1);
            likePost.setInt(1, post.getLikes());
            likePost.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel dar like na foto");
        }
    }

    public List<User> searchFriend(User user) throws SelectException {
        List<User> amigos = new ArrayList<>();
        try {
            searchFriend.setInt(1, user.getId());
            searchFriend.setInt(2, user.getId());
            ResultSet rs = searchFriend.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(3);
                select.setInt(1, id);
                ResultSet friends = select.executeQuery();
                while (friends.next()) {
                    int userId = friends.getInt(1);
                    String username = friends.getString(2);
                    String password = friends.getString(3);
                    String fullName = friends.getString(4);
                    String bio = friends.getString(5);
                    User friend = new User();
                    friend.setId(id);
                    friend.setUsername(username);
                    friend.setPassword(password);
                    friend.setFullName(fullName);
                    friend.setBio(bio);
                    amigos.add(friend);
                }

            }
            return amigos;

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar amigo");
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
            search.setString(1, user.getUsername());
            ResultSet rs = search.executeQuery();
            int quant = 0;
            while (rs.next()) {
                quant ++;
            }
            if (quant == 0) {
                int id = selectNewID();
                user.setId(id);
                insert.setInt(1, id);
                insert.setString(2, user.getUsername());
                insert.setString(3, user.getPassword());
                insert.setString(4, user.getFullName());
                insert.setString(5, user.getBio());
                insert.executeUpdate();
            } else {
                System.out.println("Nao foi possivel criar a conta: Ja existe um usuario com esse username!");
            }

        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir o usuario");
        }
    }

    public User select(int friend) throws SelectException {
        try {
            select.setInt(1, friend);
            select.executeQuery();
            ResultSet rs = select.executeQuery();
            if(rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String fullName = rs.getString(4);
                String bio = rs.getString(5);
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

    public User selectProfile(String userN, String pass) throws SelectException {
        try {

            selectProfile.setString(1, userN);
            selectProfile.setString(2, pass);
            ResultSet rs = selectProfile.executeQuery();

            if(rs.next()) {

                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String fullName = rs.getString(4);
                String bio = rs.getString(5);

                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setFullName(fullName);
                user.setBio(bio);

                return user;
            }
        } catch (SQLException e) {

            throw new SelectException("Erro ao buscar profile");

        }
        return null;
    }

    public void delete(int id) throws DeleteException {
        try {
            delete.setInt(1, id);
            deleteFriends.setInt(1, id);
            delete.executeUpdate();
            deleteFriends.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException("Nao foi possivel deletar");
        }
    }
    public void update(User user) throws UpdateException {
        try {
            update.setString(1, user.getUsername());
            update.setString(2, user.getPassword());
            update.setString(3, user.getFullName());
            update.setString(4, user.getBio());
            update.setInt(5,user.getId());
            update.executeQuery();
        } catch (SQLException e) {
            throw new UpdateException("Nao foi possivel fazer o update");
        }
    }
}
