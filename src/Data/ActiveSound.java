package Data;

import java.io.FileNotFoundException;

public interface ActiveSound {
    void login(String username, String password) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException;

    void register(String username, String password) throws UserAlreadyRegisteredException;

    void logOff(String username);

    void upload(String path) throws FileNotFoundException;

    void download(String path) throws FileNotFoundException;
}

