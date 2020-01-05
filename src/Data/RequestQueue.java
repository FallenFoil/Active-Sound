package Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RequestQueue {
    private Queue<Request> requestQueue;
    private HashMap<String,Integer> currentDownloads;
    private int downloading;
    private int requests;
    private ReentrantLock lock;
    private Condition condition;
    private int MAXDOWN;

    public RequestQueue(){
        requestQueue = new LinkedList<>();
        currentDownloads = new HashMap<>();
        requests = 0;
        downloading = 0;
        MAXDOWN = 2;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void addRequest(Request q){
        lock.lock();
        requestQueue.add(q);
        requests++;
        condition.signalAll();
        lock.unlock();

    }

    public void await(){
        try{
            condition.await();
        }
        catch (Exception e){

        }

    }

    public void nextRequest(){
        while(requests == 0) await();

        while(downloading == MAXDOWN){
            await();
        }
        if(!currentDownloads.containsKey(requestQueue.peek().getUsername())){
            lock.lock();
            Request r = requestQueue.remove();
            currentDownloads.put(r.getUsername(),r.musicId);
            condition.signalAll();
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
                condition.signalAll();
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
