package Server;

import Data.*;
//import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.HashMap;
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


    public void login(String username, String password, Socket socket)
            throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{

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

    @Override
    public void logOff(String username) {
        users.lock();
        users.get(username).offline();
        users.unlock();
    }

    @Override
    public void upload(String path) throws FileNotFoundException {

    }

    @Override
    public void download(String path) throws FileNotFoundException {

    }
}
