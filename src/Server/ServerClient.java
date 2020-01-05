package Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import Data.*;

public class ServerClient implements Runnable{
    private ActiveSound app;
    private Socket so;
    private String id;

    ServerClient(ActiveSound newApp, Socket so){
        this.app = newApp;
        this.so = so;
        this.id = null;
    }

    private String argsNotNull(String[] args, int index){
        if(index >= args.length){
            return "";
        }
        else{
            return args[index];
        }
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
                            this.app.login(argsNotNull(args, 1), argsNotNull(args, 2),so);
                            out.println("0");
                            out.flush();
                            this.id = argsNotNull(args, 1);
                            break;
                        case "register":
                            this.app.register(argsNotNull(args, 1), argsNotNull(args, 2));
                            out.println("0");
                            out.flush();
                            break;
                        case "download":
                            synchronized (this) {
                                this.app.download(Integer.parseInt(argsNotNull(args, 1)), this.id, 0);
                            }
                            break;
                        case "upload":
                            String[] realArgs = str.split(" ;");
                            synchronized (this) {
                                this.app.upload(argsNotNull(realArgs, 1), argsNotNull(realArgs, 2), Integer.parseInt(argsNotNull(realArgs, 3)), argsNotNull(realArgs, 4), argsNotNull(realArgs, 5), this.id, argsNotNull(realArgs, 7));
                            }
                            break;
                        case "search":
                            StringBuilder sb = new StringBuilder();
                            String tag;
                            if(args.length == 1){
                                tag = "";
                            }
                            else{
                                tag = args[1];
                            }
                            for(String music : this.app.search(tag)){
                                sb.append(music).append("|");
                            }
                            if(sb.length()>0){sb.deleteCharAt(sb.length()-1).append(";");}
                            out.println(sb.toString());
                            out.flush();
                            break;
                        case "logoff":
                            this.app.logOff(argsNotNull(args, 1));
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
                catch (InvalidPasswordException | UserAlreadyOnlineException | UserAlreadyRegisteredException | UserNotRegisteredException | MusicNotFoundException | DownloadErrorException | UploadErrorException e){
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
