package Server;

import Data.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActiveSound implements Data.ActiveSound {
    private Users users;
    private Musics musics;

    public ActiveSound(){
        this.users = new Users();
        this.musics = new Musics();
        this.users.put("admin", "admin");
    }

    public HashMap<Integer, Music> getMusics() {
        return musics.getMusics();
    }

    public HashMap<String, User> getUsers() {
        return users.getUsers();
    }

    public void login(String username, String password) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{

            if(!this.users.contains(username)){
                throw new UserNotRegisteredException(username);
            }

            if(this.users.get(username).isOnline()){
                throw new UserAlreadyOnlineException(username);
            }

            if(this.users.get(username).authentication(password)){

                users.lock();
                users.put(username,password);
                users.get(username).online();
                users.unlock();
            }
            else throw new InvalidPasswordException();

    }

    public void register(String username, String password) throws UserAlreadyRegisteredException{
        if(!users.contains(username)){
            users.lock();
            users.put(username,password);
            users.unlock();
        }
        else{
            throw new UserAlreadyRegisteredException(username);
        }
    }

    public void logOff(String username) {
        users.lock();
        users.get(username).offline();
        users.unlock();
    }

    public void upload(String title, String author, int year, String tags, String path) throws FileNotFoundException {

    }

    public void download(int id) throws FileNotFoundException {

    }

    public List<String> search(String tags){
        List<String> musics = new ArrayList<>();

        return musics;
    }
}
