package Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){
        File dir = new File("Uploaded");
        if(dir.exists()){
            for(File file: dir.listFiles()){
                if (!file.isDirectory()){
                    file.delete();
                }
            }
        }
        else{
            dir.mkdir();
        }

        dir = new File("Downloaded");
        if(dir.exists()){
            for(File file: dir.listFiles()){
                if (!file.isDirectory()){
                    file.delete();
                }
            }
        }
        else{
            dir.mkdir();
        }

        try{
            ServerSocket s = new ServerSocket(25567);
            ActiveSound app = new ActiveSound();
            new Thread(()->{
                while(true) {
                    app.getQueue().nextRequest();
                }
            }).start();

            while(true){
                new Thread(new ServerClient(app, s.accept())).start();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
