package Server;

import Data.*;
import javafx.util.Pair;

import java.net.Socket;
import java.util.HashMap;

public class ActiveSound {
    private Users users;
    private Musics musics;
    private OnlineUsers onlineUsers;

    public ActiveSound(){
        this.users = new Users();
        this.musics = new Musics();
        this.onlineUsers = new OnlineUsers();
        this.users.put("admin", "admin");
    }

    public HashMap<Integer, Music> getMusics() {
        return musics.getMusics();
    }

    public HashMap<String, User> getUsers() {
        return users.getUsers();
    }


    public String login(String username, String password, Socket socket){
        if(this.onlineUsers.contains(username)){
            return "User is already logged in!";
        }

        if(!this.users.contains(username)){
            return "User is not registered!";
        }

        if(this.users.get(username).authentication(password)){
            this.onlineUsers.put(username, socket);
            return "User logged in!";
        }

        return "Some error occorred logging in!";
    }

}
