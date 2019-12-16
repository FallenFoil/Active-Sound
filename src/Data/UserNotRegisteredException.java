package Data;

public class UserNotRegisteredException extends Exception {
    public UserNotRegisteredException(String user){
        super("4|User doesn't exists: " + user);
    }
}
