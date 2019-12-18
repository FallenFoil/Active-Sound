package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(25567);
            ActiveSound app = new ActiveSound();


            while(true){
                new Thread(new ServerClient(app, s.accept())).start();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
