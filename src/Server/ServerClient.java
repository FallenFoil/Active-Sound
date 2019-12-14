package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Data.InvalidPasswordException;
import Data.UserAlreadyOnlineException;
import Data.UserAlreadyRegisteredException;
import Data.UserNotRegisteredException;

public class ServerClient implements Runnable{
    private ActiveSound app;
    private Socket so;

    public ServerClient(ActiveSound newApp, Socket so){
        this.app = newApp;
        this.so = so;
    }

    public void run(){
        try{
            System.out.println("Connection established");

            BufferedReader in = new BufferedReader(new InputStreamReader(this.so.getInputStream()));
            PrintWriter out = new PrintWriter(this.so.getOutputStream());

            String str = in.readLine();

            while(!str.equals("quit")){
                System.out.println(str);
                String[] args = str.split("[|]");
                System.out.println(args[0] + " " + args[1] + " " + args[2]);
                switch (args[0]){
                    case "0":
                        try {
                            this.app.login(args[1],args[2],this.so);
                            out.println("0|Success|"+args[1]);
                            out.flush();
                        }
                        catch (UserAlreadyOnlineException | UserNotRegisteredException e){
                            out.println("0|" + e.getMessage());
                            out.flush();
                        } catch (InvalidPasswordException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "1":
                        try{
                            this.app.register(args[1],args[2]);
                            out.println("1|Success|"+args[1]);
                            out.flush();
                        }
                        catch(UserAlreadyRegisteredException e){
                            out.println("1|" + e.getMessage());
                            out.flush();
                        }
                        break;
                    default:
                        break;
                }
                str = in.readLine();
            }

            out.println("shutdown");
            out.flush();

            this.so.shutdownInput();
            this.so.shutdownOutput();
            this.so.close();

            System.out.println("Connection closed");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
