package Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class RequestQueue {
    private Queue<Request> requestQueue;
    private HashMap<String,Integer> currentDownloads;
    private int downloading;
    private int requests;
    private ReentrantLock lock;
    private int MAXDOWN;

    public RequestQueue(){
        requestQueue = new LinkedList<>();
        currentDownloads = new HashMap<>();
        requests = 0;
        downloading = 0;
        MAXDOWN = 2;
        lock = new ReentrantLock();
    }

    public void addRequest(Request q){
        lock.lock();
        requestQueue.add(q);
        requests++;
        lock.unlock();
    }

    public void nextRequest(){
        if(requests == 0) return;
        while(downloading == MAXDOWN){
            System.out.println("Tou a espera viado");
        }
        if(!currentDownloads.containsKey(requestQueue.peek().getUsername())){
            lock.lock();
            Request r = requestQueue.remove();
            currentDownloads.put(r.getUsername(),r.musicId);
            downloading++;
            requests--;
            lock.unlock();
            return;
        }
        for(Request q : requestQueue){
            if(!currentDownloads.containsKey(requestQueue.peek().getUsername())){
                lock.lock();
                requestQueue.remove(q);
                currentDownloads.put(q.getUsername(),q.getMusicId());
                downloading++;
                requests--;
                lock.unlock();
                return;
                }
            }
    }

    public boolean containsDownload(String username){
        return currentDownloads.containsKey(username);
    }

    public void removeDownload(String username){
        lock.lock();
        currentDownloads.remove(username);
        downloading--;
        lock.unlock();
    }
}
