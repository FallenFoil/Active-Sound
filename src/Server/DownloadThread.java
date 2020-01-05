package Server;

import Data.Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DownloadThread implements Runnable {

    private Music m;
    private Socket s;
    //ReentrantLock lock = new ReentrantLock();

    DownloadThread(Music m, Socket s) {
       this.m = m;
       this.s = s;
    }

    @Override
    public void run() {
            try{
                File file = new File(m.getPath());
                InputStream in = new FileInputStream(file);
                OutputStream out = s.getOutputStream();
                byte[] bytes = new byte[10*1024];
                int count;
                while((count = in.read(bytes))>0){
                    out.write(bytes,0,count);
                    out.flush();
                }
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }
}
