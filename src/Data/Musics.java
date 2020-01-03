package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Musics {
    private HashMap<Integer, Music> musics;
    private RWLock musicsLock;
    private HashMap<String, List<Integer>> tags;
    int newId;

    public Musics(){
        musics = new HashMap<>();
        musicsLock = new RWLock();
        tags = new HashMap<>();
        newId = 0;
    }

    public HashMap<Integer, Music> getMusics() {
        musicsLock.readLock();
            HashMap<Integer,Music> returnValue = new HashMap<>(musics);
        musicsLock.readUnlock();
        return returnValue;
    }

    public HashMap<String,List<Integer>> getTags(){
        musicsLock.readLock();
            HashMap<String,List<Integer>> returnValue = new HashMap<>(tags);
        musicsLock.readUnlock();
        return returnValue;
    }
    public void add(Music music){
        List<String> newTags = music.getTags();
        musicsLock.writeLock();
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
        musicsLock.writeUnlock();
    }

    public Music get(int id){
        musicsLock.readLock();
            Music toReturn =  musics.get(id);
        musicsLock.readUnlock();
        return toReturn;
    }

    public boolean contains(int id){
        musicsLock.readLock();
            boolean check = (musics.containsKey(id));
        musicsLock.readUnlock();
        return check;
    }

    public int getNewId() {
        musicsLock.writeLock();
            int toReturn = this.newId++;
        musicsLock.writeUnlock();
        return toReturn;
    }


}
