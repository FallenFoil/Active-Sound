package Data;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String user){
        super("3|User already registered: " + user);
    }
}
