package Data;

import java.io.FileNotFoundException;
import java.net.Socket;

public interface ActiveSound {
    void login(String username, String password, Socket s) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException;

    void register(String username, String password) throws UserAlreadyRegisteredException;

    void logOff(String username);

    void upload(String path) throws FileNotFoundException;

    void download(int id, String username) throws MusicNotFoundException;
}

