package Data;

public class Request {

    int musicId;
    String username;

    public Request(int i, String u){
        musicId = i;
        username = u;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getUsername() {
        return username;
    }
}
