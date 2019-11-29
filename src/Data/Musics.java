package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Musics {
    private HashMap<Integer, Music> musics;
    public ReentrantLock musicsLock = new ReentrantLock();

    public Musics(){
        musics = new HashMap<>();
    }
    public HashMap<Integer, Music> getMusics() {
        return musics;
    }


    public Music get(int id){
       return musics.get(id);
    }

}
