package Data;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class RWLock {
    private ReentrantLock lock;
    private Condition readerCondition;
    private Condition writerCondition;
    private boolean writer;
    private int readers;
    private int readersRequests;
    private int writersRequests;
    private int writingStreak;
    private int readingStreak;


    public RWLock() {
        this.lock = new ReentrantLock();
        this.readerCondition = this.lock.newCondition();
        this.writerCondition = this.lock.newCondition();
        this.writer = false;
        this.readers = 0;
        this.readersRequests = 0;
        this.writersRequests = 0;
        this.readingStreak = 0;
        this.writingStreak = 0;
    }

    public void readLock() {
        try {
            this.lock.lock();
            this.readersRequests++;
            while (this.writer || (this.writersRequests > 0 && this.readingStreak > 3)) {
                this.readerCondition.await();
            }
            this.writingStreak = 0;
            this.readingStreak++;
            this.readers++;
            this.readersRequests--;
        }catch (Exception e){

        }finally {
            this.lock.unlock();
        }
    }

    public void readUnlock() {
        try{
            this.lock.lock();
            this.readers--;
            if (this.readers == 0) {
                this.writerCondition.signal();
            }
        }catch (Exception e){

        }finally {
            this.lock.unlock();
        }
    }

    public void writeLock() {
        try {
            this.lock.lock();
            this.writersRequests++;

            while (this.readers > 0 || this.writer || (this.readersRequests > 0 && this.writingStreak > 3)) {
                this.writerCondition.await();
            }
            this.readingStreak = 0;
            this.writingStreak++;
            this.writer = true;
            this.writersRequests--;
        }catch (InterruptedException e){

        }finally {
            this.lock.unlock();
        }

    }

    public void writeUnlock() {
        try{
            this.lock.lock();
            this.writer = false;
            this.readerCondition.signal();
            this.writerCondition.signal();
        }catch (Exception e){

        }finally {
            this.lock.unlock();
        }
    }
}