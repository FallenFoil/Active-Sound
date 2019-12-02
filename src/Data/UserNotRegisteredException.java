package Data;

public class UserNotRegisteredException extends Exception {
    public UserNotRegisteredException(String user){
        super("User doesn't exists: " + user);
    }
}
