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
                            out.println("0");
                            out.flush();
                            break;
                        case "download":
                            if(!app.getMusics().containsKey(Integer.parseInt(args[1]))){
                                out.println("No");
                                out.flush();
                                break;
                            }
                            if(new File("Uploaded/" + args[1] + ".mp3").length() == 0 ){
                                out.println("No");
                                out.flush();
                                this.app.removeMusic(Integer.parseInt(args[1]));
                                break;
                            }
                            synchronized (this) {
                                this.app.download(Integer.parseInt(args[1]), this.id, 0);
                                break;
                            }
                        case "upload":
                            String[] realArgs = str.split(" ;");
                            synchronized (this) {
                                this.app.upload(realArgs[1], realArgs[2], Integer.parseInt(realArgs[3]), realArgs[4], realArgs[5], this.id, realArgs[7]);
                            }
                            break;
                        case "search":
                            StringBuilder sb = new StringBuilder();
                            if(args.length == 1){
                                out.println("");
                                out.flush();
                                break;
                            }
                            for(String music : this.app.search(args[1])){
                                sb.append(music).append("|");
                            }
                            if(sb.length()>0){sb.deleteCharAt(sb.length()-1).append(";");}
                            out.println(sb.toString());
                            out.flush();
                            break;
                        case "logoff":
                            this.app.logOff(args[1]);
                            out.println("0");
                            out.flush();
                            break;
                        case "getNotifications":
                            StringBuilder sb2 = new StringBuilder();

                            for(String notification : this.app.getNotifications()){
                                sb2.append(notification).append("|");
                            }
                            out.println(sb2.toString());
                            out.flush();
                            break;
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
                System.out.println("Connection with user: " + this.id + " force closed");
                if(this.id!=null){
                    this.app.logOff(this.id);
                }
            }
            else{
                System.out.println(e.getMessage());
            }
        }
    }
}
