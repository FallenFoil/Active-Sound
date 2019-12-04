package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Music {
    private int id;
    private String title;
    private String author;
    private int year;
    private List<String> tags;
    private int nDownloads;
    private String path;
    private int size;
    public ReentrantLock musicLock;

    public Music(int id, String titulo, String autor, int ano, List<String> etiquetas, int nDownloads, String path, int size){
        this.id = id;
        this.title = titulo;
        this.author = autor;
        this.year = ano;
        this.tags = new ArrayList<>(etiquetas);
        this.nDownloads = nDownloads;
        this.path = path;
        this.size = size;
        musicLock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public int getNDownloads() {
        synchronized (this) {
            return nDownloads;
        }
    }

    public String getPath() {
        return path;
    }

    public int size() {
        return size;
    }

    public void downloadIncrement(){
        lock();
        nDownloads++;
        unlock();
    }

    public void lock(){
        musicLock.lock();
    }

    public void unlock(){
        musicLock.unlock();
    }

}
