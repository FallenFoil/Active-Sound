package Data;

public class MusicNotFoundException extends Exception {
    public MusicNotFoundException(String id){
        super("5|Music not found id: " + id);
    }
}
