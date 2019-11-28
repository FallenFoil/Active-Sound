package Data;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Integer> downloads;
    private List<Integer> uploads;

    public User(String name, String password){
        this.username = name;
        this.password = password;
        this.downloads = new ArrayList<>();
        this.uploads = new ArrayList<>();
    }

    public User(String name, String password, List<Integer> downloads, List<Integer> uploads){
        this.username = name;
        this.password = password;
        this.downloads = new ArrayList<>(downloads);
        this.uploads = new ArrayList<>(uploads);
    }

    public String getName(){
        return username;
    }

    public List<Integer> getDownloads(){
        return downloads;
    }

    public List<Integer> getUploads(){
        return uploads;
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
}
