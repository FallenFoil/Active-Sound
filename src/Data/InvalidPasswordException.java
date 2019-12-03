package Data;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(){
        super("Wrong Password");
    }
}
