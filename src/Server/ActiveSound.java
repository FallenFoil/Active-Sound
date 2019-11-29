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


    public void login(String username, String password, Socket socket)
            throws UserAlreadyOnlineException, UserNotRegisteredException{

            if(!this.users.contains(username)){
                throw new UserNotRegisteredException();
            }

            if(this.onlineUsers.contains(username)){
                throw new UserAlreadyOnlineException();
            }

            if(this.users.get(username).authentication(password)){
                onlineUsers.onlineLock.lock();
                this.onlineUsers.put(username, socket);
                onlineUsers.onlineLock.unlock();
            }

    }

    public void register(String username, String password) throws UserAlreadyRegisteredException{
        if(!users.contains(username)){
            users.usersLock.lock();
            users.put(username,password);
            users.usersLock.unlock();
        }else throw new UserAlreadyRegisteredException();
    }

}
