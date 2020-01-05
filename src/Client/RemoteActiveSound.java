package Client;

import Data.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoteActiveSound implements ActiveSound {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public RemoteActiveSound(Socket newSocket){
        this.socket = newSocket;
        try{
            this.in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            this.out = new PrintWriter(newSocket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Done. Needs verification
    public void login(String username, String password, Socket socket) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{
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

    //Done. Needs verification
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

    //Done. Needs verification
    public void logOff(String username){
        this.out.println("logoff " + username);
        this.out.flush();

        try{
            String str = in.readLine();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    //Not Done
    public void upload(String title, String author, int year, String tags, String path, String username, String size) throws FileNotFoundException{
        String[] pathSplitted = path.split("[.]");
        if(pathSplitted.length == 1) throw new FileNotFoundException("invalid file format");
        if(!pathSplitted[1].equals("mp3")) throw new FileNotFoundException("invalid file format");

        String newPath = "ToUpload/" + path;
        File toUpload = new File(newPath);
        long fileSize = toUpload.length();
        if(fileSize == 0) throw new FileNotFoundException("File not found");
        this.out.println("upload " + title + " " + author + " " + year + " " + tags + " " + path + " " + username + " " + fileSize);
        this.out.flush();

        try {
            InputStream in = new FileInputStream(toUpload);
            OutputStream out = socket.getOutputStream();
            byte[] bytes = new byte[10 * 1024];
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
                out.flush();
            }
            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Not Done
    public void download(int id, String username, int size) throws MusicNotFoundException {
        this.out.println("download " + id);
        this.out.flush();

        try {
            String str = this.in.readLine();
            if(str.equals("No")){
                System.out.println("Music " + id + " not found");
            }else {
                int fileSize = Integer.parseInt(str.split(" ")[2]);
                this.out.println("ok");
                this.out.flush();

                InputStream fin = socket.getInputStream();
                File targetDir = new File("Downloaded");
                File file = new File(targetDir, username + id + ".mp3");
                FileOutputStream fout = new FileOutputStream(file);

                byte[] bytes = new byte[16 * 1024];
                int count, x = 0;
                while (x < fileSize && (count = fin.read(bytes)) > 0) {
                    fout.write(bytes, 0, count);
                    x += count;
                    fout.flush();
                }
                fout.flush();
                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Done. Needs verification
    public void exit(){
        this.out.println("exit");
        this.out.flush();
    }

    //Done. Needs verification
    public List<String> search(String tag){
        this.out.println("search " + tag.toLowerCase().replaceAll("\\s+", ""));
        this.out.flush();
        List<String> musics = new ArrayList<>();
        try{
            String str = in.readLine();
            if(!str.isEmpty()){
                String[] args = str.split("[|]");
                musics.addAll(Arrays.asList(args));
            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return musics;
    }
}
