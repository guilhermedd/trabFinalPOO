package dados;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Post> feed = new ArrayList<>();
    private String fullName;
    private String bio;

    public void setFeed(List<Post> feed) {
        this.feed = feed;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<User> friends = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private int id;

    public User(){}


    public List<Post> getPosts() {
        return posts;
    }


    public void setPosts(Post posts) {
        this.posts.add(posts);
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(User friends) {
        this.friends.add(friends);
    }

    public void setFeed(Post feed) {
        this.feed.add(feed);
    }

    public List<Post> getFeed() {
        return feed;
    }

    public void postFoto(Post post) {
        this.feed.add(post);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isEqual(User user) {
        if (user.getUsername().equals(this.username)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Nome: " + this.fullName;
    }
}
