package Data;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(){
        super("1|Wrong Password");
    }
}
