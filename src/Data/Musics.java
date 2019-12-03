package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Musics {
    private HashMap<Integer, Music> musics;
    private ReentrantLock musicsLock = new ReentrantLock();
    int newId;

    public Musics(){
        musics = new HashMap<>();
        newId = 0;
    }
    public HashMap<Integer, Music> getMusics() {
        return musics;
    }

    public void add(Music music){
        lock();
        musics.put(newId++,music);
        unlock();
    }
    public Music get(int id){
       return musics.get(id);
    }


    public void lock(){
        musicsLock.lock();
    }
    public void unlock(){
        musicsLock.unlock();
    }
}
