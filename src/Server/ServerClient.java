package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerClient implements Runnable{
    private Socket so;

    public ServerClient(Socket so){
        this.so = so;
    }

    public void run(){
        try{
            System.out.println("Connection established");

            BufferedReader in = new BufferedReader(new InputStreamReader(this.so.getInputStream()));

            String str = in.readLine();
            System.out.println(str);

            this.so.shutdownInput();
            this.so.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
