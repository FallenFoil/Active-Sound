package Server;

import Data.Music;
import Data.User;

import java.util.HashMap;

public class ActiveSound {
    private HashMap<String, User> users;
    private HashMap<Integer, Music> musics;

    public ActiveSound(){
        users = new HashMap<>();
        musics = new HashMap<>();
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

}
