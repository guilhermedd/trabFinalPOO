package negocio;
import dados.*;
import persistencia.*;
import sistema.*;

import java.util.ArrayList;
import java.util.List;

public class Aplication {

    public Aplication(){}

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public boolean createUser(User user) {
        for(User listed: getUsers()){
            if (listed.isEqual(user)) {
                return false;
            }
        }
        return true;
    }

    public User login(String username, String password) {
        if (users.size() < 1) return null;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return user;
        }
        return null;
    }

    public void postFoto(User user, Post post) {
        user.setPosts(post);
        for(User friend: user.getFriends()) {
            if (friend.getFeed().size() > 100) friend.getFeed().remove(friend.getFeed().get(0));
            friend.setFeed(post);
        }
    }

    public User searchUser(String username) {
        for(User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean follow(User user, User friend) {
        if (!user.getFriends().contains(friend)){
            user.setFriends(friend);
            return true;
        }
        return false;
    }

    public boolean unfollow(User user, User unfriend) {
        if (user.getFriends().contains(unfriend)){
            user.getFriends().remove(unfriend);
            return true;
        }
        return false;
    }

    public User showProfile(User user, User friend) {
        if (user.getFriends().contains(friend)) {
            return user;
        }
        return null;
    }

    public boolean isFollowing(User user, User friend) {
        if (user.getFriends().contains(friend)) {
            return true;
        }
        return false;
    }


}
