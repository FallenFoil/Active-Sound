package Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import Data.*;

public class ServerClient implements Runnable{
    private ActiveSound app;
    private Socket so;
    private String id;

    public ServerClient(ActiveSound newApp, Socket so){
        this.app = newApp;
        this.so = so;
        this.id = null;
    }

    public void run(){
        try{
            System.out.println("Connection established");

            BufferedReader in = new BufferedReader(new InputStreamReader(this.so.getInputStream()));
            PrintWriter out = new PrintWriter(this.so.getOutputStream());

            String str = in.readLine();

            while(!str.equals("exit")){
                String[] args = str.split(" ");
                try {
                    switch (args[0]) {
                        case "login":
                            this.app.login(args[1], args[2],so);
                            out.println("0");
                            out.flush();
                            this.id = args[1];
                            break;
                        case "register":
                            this.app.register(args[1], args[2]);
                            out.println(0);
                            out.flush();
                            break;
                        case "download":
                            this.app.download(Integer.parseInt(args[1]),this.id);
                        default:
                            break;
                    }
                }
                catch (InvalidPasswordException | UserAlreadyOnlineException | UserAlreadyRegisteredException | UserNotRegisteredException | MusicNotFoundException e){
                    out.println(e.getMessage());
                    out.flush();
                }
                str = in.readLine();
            }

            this.so.shutdownInput();
            this.so.shutdownOutput();
            this.so.close();

            System.out.println("Connection closed");
        }
        catch (IOException e) {
            if(e instanceof SocketException){
                System.out.println("Connection force closed");
                if(this.id!=null){
                    this.app.getSessions().remove(this.id);
                }
            }
            else{
                System.out.println(e.getMessage());
            }
        }
    }
}
