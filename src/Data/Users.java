package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Users {
    private RWLock usersLock;
    private HashMap<String, User> users;

    public Users(){
        users = new HashMap<>();
        usersLock = new RWLock();
    }

    public void put(String username, String password){
        usersLock.writeLock();
            users.put(username,new User(username,password));
        usersLock.writeUnlock();
    }

    public User get(String username){
        usersLock.readLock();
            User toReturn = users.get(username);
        usersLock.readUnlock();
        return toReturn;
    }

    public boolean contains(String username){
        usersLock.readLock();
            boolean check = users.containsKey(username);
        usersLock.readUnlock();
        return check;
    }


    public HashMap<String, User> getUsers() {
        usersLock.readLock();
            HashMap<String,User> toReturn = new HashMap<>(users);
        usersLock.readUnlock();
        return toReturn;
    }
}
