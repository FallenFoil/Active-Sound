package Server;

import Data.*;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.HashMap;

public class ActiveSound implements Data.ActiveSound {
    private Users users;
    private Musics musics;
    private HashMap<String, Socket> sessions;

    public ActiveSound(){
        this.users = new Users();
        this.musics = new Musics();
        this.sessions = new HashMap<>();
        this.users.put("admin", "admin");
    }

    public HashMap<Integer, Music> getMusics() {
        return musics.getMusics();
    }

    public HashMap<String, User> getUsers() {
        return users.getUsers();
    }

    public HashMap<String, Socket> getSessions(){
        return new HashMap<>(this.sessions);
    }

    public void login(String username, String password,Socket s) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{

            if(!this.users.contains(username)){
                throw new UserNotRegisteredException(username);
            }

            if(this.sessions.containsKey(username)){
                throw new UserAlreadyOnlineException(username);
            }

            if(this.users.get(username).authentication(password)){
                users.lock();
                users.put(username,password);
                sessions.put(username,s);
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
        sessions.remove(username);
    }

    public void upload(String path) throws FileNotFoundException {

    }

    public void download(int id, String username) throws MusicNotFoundException {
        if(!musics.contains(id)){
                throw new MusicNotFoundException(Integer.toString(id));
        }
        try {
            Music toDownload = musics.get(id);
            Socket s = sessions.get(username);
            Thread download = new Thread(new DownloadThread(toDownload, s));
            download.start();
            download.join();
        }catch (Exception e){

        }

    }
}
