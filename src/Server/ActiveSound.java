package Server;

import Data.*;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActiveSound implements Data.ActiveSound {
    private Users users;
    private Musics musics;
    private HashMap<String, Socket> sessions;
    private Lock sessionsLock;
    private volatile RequestQueue queue = new RequestQueue();
    private LinkedList<String> notifications;
    private Lock notificationsLock;

    public ActiveSound(){
        this.users = new Users();
        this.musics = new Musics();
        this.sessions = new HashMap<>();
        this.sessionsLock = new ReentrantLock();
        this.users.put("admin", "admin");
        populateServer();
        this.notifications = new LinkedList<>();
        this.notificationsLock = new ReentrantLock();
    }

    public void populateServer(){
        List<String> tags = new ArrayList<>();
        Music music;

//Music 1
        /*
        tags.add("rock");
        music = new Music(musics.getNewId(), "Uprising", "Muse", 2009, tags, 10475, "String path", 10);
        this.musics.add(music);

//Music 2
        tags.clear();
        tags.add("electro");
        music = new Music(musics.getNewId(), "Secret Agent", "Odd Chap", 2017, tags, 10, "String path", 10);
        this.musics.add(music);

//Music 3
        tags.clear();
        tags.add("electro");
        music = new Music(musics.getNewId(), "Sandstorm", "Darude", 2000, tags, 104, "String path", 10);
        this.musics.add(music);

//Music 4
        tags.clear();
        tags.add("rock");
        music = new Music(musics.getNewId(), "Seven Nation Army", "White Stripes", 2003, tags, 1047, "String path", 10);
        this.musics.add(music);*/

//Music 5
        tags.clear();
        tags.add("jazz");
        music = new Music(musics.getNewId(), "Name4", "Author4", 2004, tags, 14, "String path", 10);
        this.musics.add(music);

//Music 6
        tags.clear();
        tags.add("jazz");
        music = new Music(musics.getNewId(), "Name5", "Author5", 2005, tags, 15, "String path", 10);
        this.musics.add(music);

//Music 7
        tags.clear();
        tags.add("rock");
        music = new Music(musics.getNewId(), "Name6", "Author6", 2006, tags, 16, "String path", 10);
        this.musics.add(music);

//Music 8
        tags.clear();
        tags.add("pop");
        music = new Music(musics.getNewId(), "Name7", "Author7", 2007, tags, 17, "String path", 10);
        this.musics.add(music);

//Music 9
        tags.clear();
        tags.add("edm");
        music = new Music(musics.getNewId(), "Name8", "Author8", 2008, tags, 18, "String path", 10);
        this.musics.add(music);

//Music 10
        tags.clear();
        tags.add("jazz");
        music = new Music(musics.getNewId(), "Name9", "Author9", 2009, tags, 19, "String path", 10);
        this.musics.add(music);

//Music 11
        tags.clear();
        tags.add("rock");
        music = new Music(musics.getNewId(), "Name10", "Author10", 2010, tags, 110, "String path", 10);
        this.musics.add(music);

//Music 12
        tags.clear();
        tags.add("rock");
        tags.add("jazz");
        music = new Music(musics.getNewId(), "Name11", "Author11", 2011, tags, 111, "String path", 10);
        this.musics.add(music);
    }

    public HashMap<Integer, Music> getMusics() {
        return musics.getMusics();
    }

    public HashMap<String, User> getUsers() {
        return users.getUsers();
    }

    public HashMap<String, Socket> getSessions(){
        return new HashMap<>(this.sessions);
    }

    public void login(String username, String password,Socket s) throws UserAlreadyOnlineException, UserNotRegisteredException, InvalidPasswordException{

            if(!this.users.contains(username)){
                throw new UserNotRegisteredException(username);
            }

            if(this.sessions.containsKey(username)){
                throw new UserAlreadyOnlineException(username);
            }

            if(this.users.get(username).authentication(password)){
                users.put(username,password);
                sessionsLock.lock();
                sessions.put(username,s);
                sessionsLock.unlock();
            }
            else throw new InvalidPasswordException();

    }

    public void register(String username, String password) throws UserAlreadyRegisteredException{
        if(!users.contains(username)){
            users.put(username,password);
        }
        else{
            throw new UserAlreadyRegisteredException(username);
        }
    }
    
    public void logOff(String username){
        this.sessionsLock.lock();
        sessions.remove(username);
        this.sessionsLock.unlock();
    }

    public void upload(String title, String author, int year, String tags, String path, String username ,String size) throws FileNotFoundException {
        List<String> tagsSplitted = new ArrayList<>(Arrays.asList( tags.split("[,]")));
        int newId = musics.getNewId();
        String newPath = "Uploaded/" + newId + ".mp3";
        int fileSize = Integer.parseInt(size);
        Music toUpload = new Music(newId, title, author, year, tagsSplitted, 0,newPath, fileSize);
        Socket s = sessions.get(username);

        try {
            File targetDir = new File("Uploaded");
            File file = new File(targetDir, Integer.toString(newId) + ".mp3");
            FileOutputStream fout = new FileOutputStream(file);
            InputStream fin = s.getInputStream();

            byte[] bytes = new byte[16 * 1024];
            int count, x = 0;
            while (x < fileSize &&(count = fin.read(bytes)) > 0 ) {
                x += count;
                fout.write(bytes, 0, count);
                fout.flush();
            }
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.musics.add(toUpload);
        this.users.get(username).addUpload(toUpload.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.notificationsLock.lock();
        this.notifications.addFirst(title + ";" + author + ";" + tags + ";" + formatter.format(new Date()));
        this.notificationsLock.unlock();
    }

    public void download(int id, String username, int size) throws MusicNotFoundException {
        if(!musics.contains(id)){
                throw new MusicNotFoundException(Integer.toString(id));
        }
        try {
            Music toDownload = musics.get(id);
            Socket s = sessions.get(username);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            out.println("Preparing download " + toDownload.size());
            out.flush();
            if(new BufferedReader(new InputStreamReader(s.getInputStream())).readLine().equals("ok")){
                queue.addRequest(new Request(id,username));
                while(!queue.containsDownload(username)){
                }
                Thread download = new Thread(new DownloadThread(toDownload,s));
                download.start();
                queue.removeDownload(username);
                toDownload.downloadIncrement();
                download.join();
                users.get(username).addDownload(id);
            }
        }catch (Exception e){

        }

    }

    public List<String> search(String tag){
        if(!musics.getTags().containsKey(tag)) return new ArrayList<>();
        List<Integer> musics = new ArrayList<>(this.musics.getTags().get(tag));
        List<String> musicsString = new ArrayList<>();
        for(Integer m : musics){
           musicsString.add(this.musics.get(m).toString());
        }
        return musicsString;
    }

    public void removeMusic(int id){
        this.musics.remove(id);
    }

    public RequestQueue getQueue(){
        return queue;
    }

    public List<String> getNotifications(){
        this.notificationsLock.lock();
        List<String> res = new ArrayList<>(this.notifications);
        this.notificationsLock.unlock();

        return res;
    }
}
