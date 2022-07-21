package dados;

public class Comment {
    public String getCom() {
        return com;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public void setCom(String com) {
        this.com = com;
    }
    private int idPost;
    private String com;
}
