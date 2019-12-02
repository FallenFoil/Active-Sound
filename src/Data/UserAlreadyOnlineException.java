package Data;

public class UserAlreadyOnlineException extends Exception {
    public UserAlreadyOnlineException(String user){
        super("User already logged in: " + user);
    }
}
