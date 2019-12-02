package Data;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String user){
        super("User already registered: " + user);
    }
}
