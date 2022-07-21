package dados;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String image;
    private String description;
    private List<User> favUserList = new ArrayList<>();
    private List<String> comments;
    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    private int id;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    private int idUsuario;

    public void setFavUserList(List<User> favUserList) {
        this.favUserList = favUserList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getFavUserList() {
        return favUserList;
    }

    public void favPost(User favUserList) {
        this.favUserList.add(favUserList);
    }

    public int getQuantFav() {
        return favUserList.size();
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String toString() {
        return "Descricao: " + this.description;
    }
}
