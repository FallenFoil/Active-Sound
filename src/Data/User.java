package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class User {

    private String username;
    private String password;
    private List<Integer> downloads;
    private List<Integer> uploads;
    private boolean online;
    public ReentrantLock userLock;

    public User(String name, String password){
        this.username = name;
        this.password = password;
        this.downloads = new ArrayList<>();
        this.uploads = new ArrayList<>();
        this.online = false;
        userLock = new ReentrantLock();
    }

    public User(String name, String password, List<Integer> downloads, List<Integer> uploads){
        this.username = name;
        this.password = password;
        this.downloads = new ArrayList<>(downloads);
        this.uploads = new ArrayList<>(uploads);
        this.online = false;
    }

    public String getName(){
        return username;
    }

    public List<Integer> getDownloads(){
        return new ArrayList<>(downloads);
    }

    public List<Integer> getUploads(){
        return new ArrayList<>(uploads);
    }

    public void addDownload(int id){
        downloads.add(id);
    }

    public void addUpload(int id){
        uploads.add(id);
    }

    public boolean authentication(String password){
        return password.equals(this.password);
    }

    public boolean isOnline(){
        return online;
    }

    public void online(){
        online = true;
    }

    public void offline(){
        online = false;
    }

    public void lock(){
        userLock.lock();
    }

    public void unlock(){
        userLock.unlock();
    }
}
