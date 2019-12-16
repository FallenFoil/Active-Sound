package Data;

public class UserAlreadyOnlineException extends Exception {
    public UserAlreadyOnlineException(String user){
        super("2|User  already logged in: " + user);
    }
}
