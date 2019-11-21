package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            PrintWriter out = new PrintWriter(this.so.getOutputStream());

            String str = in.readLine();
            System.out.println(str);

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
