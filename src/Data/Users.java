package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Users {
    private ReentrantLock usersLock;
    private HashMap<String, User> users;

    public Users(){
        users = new HashMap<>();
        usersLock = new ReentrantLock();
    }

    public void put(String username, String password){
        lock();
        users.put(username,new User(username,password));
        unlock();
    }

    public User get(String username){
        synchronized (this) {
            return users.get(username);
        }
    }

    public boolean contains(String username){
        synchronized (this) {
            return users.containsKey(username);
        }
    }

    public void lock(){
        usersLock.lock();
    }

    public void unlock(){
        usersLock.unlock();
    }

    public HashMap<String, User> getUsers() {
        return new HashMap<>(users);
    }
}
