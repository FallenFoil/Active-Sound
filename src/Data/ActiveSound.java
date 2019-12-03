package Data;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.HashMap;

public interface ActiveSound {

    HashMap<Integer, Music> getMusics();

    HashMap<String, User> getUsers();

    void login(String username, String password, Socket socket)
            throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException;

    void register(String username, String password)
            throws UserAlreadyRegisteredException;

    void logOff(String username);

    void upload(String path)
            throws FileNotFoundException;

    void download(String path)
            throws FileNotFoundException;

}

