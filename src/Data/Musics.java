package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Musics {
    private HashMap<Integer, Music> musics;
    private ReentrantLock musicsLock;
    private HashMap<String, List<Integer>> tags;
    int newId;

    public Musics(){
        musics = new HashMap<>();
        musicsLock = new ReentrantLock();
        tags = new HashMap<>();
        tags = new HashMap<>();
        newId = 0;
    }

    public HashMap<Integer, Music> getMusics() {
        synchronized(this) {
            return new HashMap<>(musics);
        }
    }

    public HashMap<String,List<Integer>> getTags(){
        synchronized (this){
            return new HashMap<>(tags);
        }
    }
    public void add(Music music){
        List<String> newTags = music.getTags();
        lock();
        for(String tag : newTags){
            if(tags.containsKey(tag)){
                if(!tags.get(tag).contains(music.getId())) {
                    tags.get(tag).add(music.getId());
                }
            }else{
                ArrayList<Integer> newMusicTag = new ArrayList<>();
                newMusicTag.add(music.getId());
                tags.put(tag,newMusicTag);
            }
        }
        musics.put(music.getId(),music);
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

    public int getNewId() {
        synchronized (this){
            return newId++;
        }
    }

    public int currentId(){
        synchronized (this){
            return newId;
        }
    }

    public void lock(){
        musicsLock.lock();
    }

    public void unlock(){
        musicsLock.unlock();
    }
}
