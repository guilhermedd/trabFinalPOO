package sistema;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;
import persistencia.*;
import dados.*;

import java.sql.SQLException;
import java.util.List;

public class Sistema {
    Conexao con = new Conexao();
    public void setSenha (String senha) {
        Conexao.setSenha(senha);
    }
    public void createUser(User user) throws SQLException, ClassNotFoundException {
       try{
           UserDAO.getInstance().insert(user);
       } catch (SQLException e) {
           throw new ClassNotFoundException("Nao deu pra criar o usuario");
       } catch (InsertException e) {
           e.printStackTrace();
       } catch (SelectException e) {
           e.printStackTrace();
       }
       //😂❤❤🤞
    }
    public void like(Post post) {
        try {
            UserDAO.getInstance().likePost(post);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }

    public User selectUser(int i) throws SQLException, ClassNotFoundException {
        User aux;
        try{
            aux = UserDAO.getInstance().select(i);
            return aux;
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> searchFriends(User user) throws SQLException, ClassNotFoundException {
        List<User> aux;
        try{
            aux = UserDAO.getInstance().searchFriend(user);
            return aux;
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectProfile(String userN, String pass) throws SQLException, ClassNotFoundException {
        User aux;
        try{
            aux = UserDAO.getInstance().selectProfile(userN, pass);
            return aux;
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectProfileInt(int id) throws SQLException, ClassNotFoundException {
        User aux;
        try{
            aux = UserDAO.getInstance().select(id);
            return aux;
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteUser(int id) throws SQLException, ClassNotFoundException {
        try{
            UserDAO.getInstance().delete(id);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (DeleteException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        try{
            UserDAO.getInstance().update(user);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }
    public void follow(User user, User friend) throws SQLException, ClassNotFoundException {
        try{
            UserDAO.getInstance().follow(user, friend);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (InsertException e) {
            e.printStackTrace();
        }
    }
    public void createComment(Comment comment) throws SQLException, ClassNotFoundException {
        try{
            CommentDAO.getInstance().insert(comment);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (InsertException e) {
            e.printStackTrace();
        } catch (SelectException e) {
            e.printStackTrace();
        }
    }
    public void selectComment(Comment comment) throws SQLException, ClassNotFoundException {
        try{
            CommentDAO.getInstance().select(comment.getIdPost());
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        }
    }
    public void updatetComment(Comment comment) throws SQLException, ClassNotFoundException {
        try{
            CommentDAO.getInstance().update(comment);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }
    public void createPost(Post post, User user, List<User> friends) throws SQLException, ClassNotFoundException {
        try{
            PostDAO.getInstance().insert(post, user, friends);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (InsertException e) {
            e.printStackTrace();
        } catch (SelectException e) {
            e.printStackTrace();
        }
    }
    public void deletePost(Post post) throws SQLException, ClassNotFoundException {
        try{
            PostDAO.getInstance().delete(post);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        } catch (DeleteException e) {
            e.printStackTrace();
        }
    }
    public void updatePost(Post post) throws SQLException, ClassNotFoundException {
        try{
            PostDAO.getInstance().update(post);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } catch (SelectException e) {
            e.printStackTrace();
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }
    public Post selectPost(Post post) {
        try{
            return PostDAO.getInstance().select(post.getId());
        } catch (SelectException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Comment> selectAllPost(Post post) {
        try{
            return PostDAO.getInstance().selectAll();
        } catch (SelectException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addPass(String pass) {
        Conexao.setSenha(pass);
    }
}
