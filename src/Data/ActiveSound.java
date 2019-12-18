package Data;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.List;

public interface ActiveSound {
    void login(String username, String password, Socket s) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException;

    void register(String username, String password) throws UserAlreadyRegisteredException;

    void logOff(String username);

    void upload(String title, String author, int year, String tags, String path) throws FileNotFoundException;

    void download(int id, String username) throws MusicNotFoundException;

    //A String tags tem o seguinte formato: <tag>|<tag>|<tag>|...
    List<String> search(String tags);
}

