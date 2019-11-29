package Data;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class OnlineUsers {
    private HashMap<String, Socket> users;
    public ReentrantLock onlineLock = new ReentrantLock();

    public OnlineUsers(){
        users = new HashMap<>();
    }
    public HashMap<String, Socket> getUsers() {
        return users;
    }

    public boolean contains(String username){
        return users.containsKey(username);
    }

    public void put(String username, Socket socket){
        users.put(username,socket);
    }
}
