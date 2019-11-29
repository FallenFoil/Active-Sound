package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Users {
    public ReentrantLock usersLock = new ReentrantLock();
    private HashMap<String, User> users;

    public Users(){
        users = new HashMap<>();
    }

    public void put(String username, String password){
        users.put(username,new User(username,password));
    }

    public User get(String username){
        return users.get(username);
    }

    public boolean contains(String username){
        return users.containsKey(username);
    }


    public HashMap<String, User> getUsers() {
        return users;
    }
}
