package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private Socket socket;
    private ReentrantLock lock;
    private Condition condition;
    private volatile Menu menu;
    private BufferedReader in;
    private PrintWriter out;
    private boolean quit;

    public Client(Socket newSocket){
        this.socket = newSocket;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.menu = null;
        this.quit = false;
        try{
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void authentication(){
        this.menu = new Menu("ActiveSound");

        this.menu.addOption("Login", ()->{
            Scanner scan = new Scanner(System.in);
            System.out.print("Username:\n$ ");
            String username = scan.nextLine();
            System.out.print("Password:\n$ ");
            String password = scan.nextLine();

            String str = "0|" + username + "|" + password;

            this.out.println(str);
            this.out.flush();
        });

        this.menu.addOption("Register", ()->{
            Scanner scan = new Scanner(System.in);
            System.out.print("Username:\n$ ");
            String username = scan.nextLine();
            System.out.print("Password:\n$ ");
            String password = scan.nextLine();

            String str = "1|" + username + "|" + password;

            this.out.println(str);
            this.out.flush();
        });

        this.menu.addOption("Exit", "exit;color=150,0,0;", ()->{
            this.out.println("quit");
            this.out.flush();
        });
    }

    private void mainMenu(){
        this.lock.lock();
        this.menu.clear();

        this.menu.addOption("Search", ()->{
            System.out.println("Search");
        });

        this.menu.addOption("Download", ()->{
            System.out.println("Download");
        });

        this.menu.addOption("Upload", ()->{
            System.out.println("Upload");
        });

        this.menu.addOption("Log Out", ()->{
            System.out.println("Log Out");
        });

        this.menu.addOption("Exit App", ()->{
            this.out.println("quit");
            this.out.flush();
        });

        this.condition.signal();
        this.lock.unlock();
    }

    private void searchMenu(){
        this.menu.addOption("Next Page", ()->{
            System.out.println("Next page");
        });

        this.menu.addOption("Previous Page", ()->{
            System.out.println("Previous page");
        });

        this.menu.addOption("Listen", ()->{
            System.out.println("Listen");
        });

        this.menu.addOption("Download", ()->{
            System.out.println("Download");
        });

        this.menu.addOption("Back", ()->{
            this.menu.clear();
            authentication();
        });
    }

    private Thread read(){
        return new Thread(()->{
            try{
                String str = this.in.readLine();
                while(!str.equals("shutdown")){
                    String[] args = str.split("[|]");
                    switch (args[0]){
                        case "0":
                            if(args[1].equals("Success")){
                                System.out.println("Welcome, " + args[2]);
                                mainMenu();
                            }
                            else{
                                System.out.println(args[1]);
                                this.lock.lock();
                                this.condition.signal();
                                this.lock.unlock();
                            }
                            break;
                        case "1":
                            if(args[1].equals("Sucess")){
                                System.out.println("User " + args[2] + " has been registered");
                            }
                            else{
                                System.out.println(args[1]);
                            }
                            this.lock.lock();
                            this.condition.signal();
                            this.lock.unlock();
                            break;
                        default:
                            break;
                    }
                    str = in.readLine();
                }

                System.out.println("Shutting Down");
                this.lock.lock();
                this.quit = true;
                this.condition.signal();
                this.lock.unlock();

                this.socket.shutdownOutput();
                this.socket.shutdownInput();
                this.socket.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private Thread write(){
        return new Thread(()->{
            while(!this.quit){
                try{
                    this.lock.lock();
                    if(this.menu == null){
                        authentication();
                    }
                    this.menu.start();
                    this.condition.await();
                }
                catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
                finally{
                    this.lock.unlock();
                }
            }
        });
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("127.0.0.1", 25567);
            Client client = new Client(s);
            client.read().start();
            client.write().start();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
