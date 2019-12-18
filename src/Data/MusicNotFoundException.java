package Data;

public class MusicNotFoundException extends Exception {
    public MusicNotFoundException(String id){
        super("Music not found id: " + id);
    }
}
