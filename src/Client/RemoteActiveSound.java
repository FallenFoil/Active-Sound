package Client;

import Data.*;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.Socket;

public class RemoteActiveSound implements ActiveSound {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public RemoteActiveSound(Socket newSocket){
        this.socket = newSocket;
        try{
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login(String username, String password) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{
        this.out.println("login " + username + " " + password);
        this.out.flush();

        try{
            String str = in.readLine();
            String[] args = str.split("[|]");
            switch(args[0]){
                case "1":
                    throw new InvalidPasswordException();
                case "2":
                    throw new UserAlreadyOnlineException(username);
                case "4":
                    throw new UserNotRegisteredException(username);
                default:
                    break;
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void register(String username, String password) throws UserAlreadyRegisteredException{
        this.out.println("register " + username + " " + password);
        this.out.flush();

        try{
            String str = in.readLine();
            String[] args = str.split("[|]");
            if(args[0].equals("3")){
                throw new UserAlreadyRegisteredException(username);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void logOff(String username){

    }

    public void upload(String path) throws FileNotFoundException{

    }

    public void download(String path) throws FileNotFoundException{

    }

    public void exit(){
        this.out.println("exit");
        this.out.flush();
    }
}
