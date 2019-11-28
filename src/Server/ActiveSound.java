package Server;

import Data.Music;
import Data.User;
import javafx.util.Pair;

import java.net.Socket;
import java.util.HashMap;

public class ActiveSound {
    private HashMap<String, User> users;
    private HashMap<Integer, Music> musics;
    private HashMap<String, Socket> onlineUsers;

    public ActiveSound(){
        this.users = new HashMap<>();
        this.musics = new HashMap<>();
        this.onlineUsers = new HashMap<>();
        this.users.put("admin", new User("admin", "admin"));
    }

    public HashMap<Integer, Music> getMusics() {
        return musics;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.put(user.getName(),user);
    }

    public void addMusic(Music music){
        musics.put(music.getId(),music);
    }

    public User getUser(String name){
        return users.get(name);
    }

    public Music getMusic(int id){
        return musics.get(id);
    }

    public Pair<Boolean, String> login(String username, String password, Socket socket){
        if(this.onlineUsers.containsKey(username)){
            return new Pair<>(false, "User is already logged in!");
        }

        if(!this.users.containsKey(username)){
            return new Pair<>(false, "User is not registered!");
        }

        if(this.users.get(username).authentication(password)){
            this.onlineUsers.put(username, socket);
            return new Pair<>(true,"User logged in!");
        }

        return new Pair<>(false, "Some error occorred logging in!");
    }

}
