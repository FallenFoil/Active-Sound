package Data;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Musics {
    private HashMap<Integer, Music> musics;
    private ReentrantLock musicsLock;
    int newId;

    public Musics(){
        musics = new HashMap<>();
        musicsLock = new ReentrantLock();
        newId = 0;
    }
    public HashMap<Integer, Music> getMusics() {
        synchronized (this) {
            return new HashMap<>(musics);
        }
    }

    public void add(Music music){
        lock();
        musics.put(newId++,music);
        unlock();
    }
    public Music get(int id){
        synchronized (this) {
            return musics.get(id);
        }
    }

    public boolean contains(int id){
        return (musics.containsKey(id));
    }

    public void lock(){
        musicsLock.lock();
    }
    public void unlock(){
        musicsLock.unlock();
    }
}
