package Client;

import Data.*;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Client{
    private RemoteActiveSound activeSound;
    private Menu menu;
    private String username;
    private Socket socket;

    private Client(Socket s){
        this.socket = s;
        this.activeSound = new RemoteActiveSound(s);
        this.menu = new Menu("ActiveSound");
        this.username = null;
    }

    private int validIntegerInput(String str, Scanner scan, int impossibleValue){
        int value = impossibleValue;
        while(value==impossibleValue){
            try{
                System.out.print(str);
                String yearStr = scan.nextLine();
                value = Integer.parseInt(yearStr);
            }
            catch(NumberFormatException e){
                System.out.println("Invalid Input");
            }
        }

        return value;
    }

    private void searchPage(List<String> musics){
        Scanner scan = new Scanner(System.in);
        this.menu.clear();

        List<String> header = new ArrayList<>();
        header.add("Id");
        header.add("Title");
        header.add("Author");
        header.add("Year");
        header.add("Tags");
        header.add("N. Downloads");
        this.menu.addTableHeader(header);

        if(!musics.isEmpty()){
            for(String str : musics){
                List<String> music = Arrays.asList(str.split("[;]"));
                this.menu.addTableData(music);
            }
        }

        this.menu.addOption("Next Page", ()->{
            this.menu.increaseMinMax();
            this.menu.start();
        });

        this.menu.addOption("Previous Page", ()->{
            this.menu.decreaseMinMax();
            this.menu.start();
        });

        this.menu.addOption("Download", ()->{
            System.out.println("Music's IDs (Separated by ','):\n$ ");
            String ids = scan.nextLine();
            String[] idsSplit = ids.split("[,]");
            try {
                for(String id : idsSplit) {
                    this.activeSound.download(Integer.parseInt(id), this.username, 0);
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        this.menu.addOption("Back", this::mainPage);

        this.menu.start();
    }

    private void mainPage(){
        Scanner scan = new Scanner(System.in);
        this.menu.clear();

        this.menu.addOption("Search", ()->{
            System.out.print("Music tag:\n$ ");
            String tag = scan.nextLine();
            searchPage(this.activeSound.search(tag));
        });

        this.menu.addOption("Download", ()->{
            System.out.println("Music's IDs (Separated by ','):\n$ ");
            String ids = scan.nextLine();
            String[] idsSplit = ids.split("[,]");
            try {
                for(String id : idsSplit) {
                    this.activeSound.download(Integer.parseInt(id), this.username, 0);
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        this.menu.addOption("Upload", ()->{
            System.out.print("Title:\n$ ");
            String title = scan.nextLine();

            System.out.print("Author:\n$ ");
            String author = scan.nextLine();

            int year = validIntegerInput("Year:\n$ ", scan, -1);

            System.out.print("Tags (separated by '|'):\n$ ");
            String tags = scan.nextLine();

            System.out.print("Path:\n$ ");
            String path = scan.nextLine();

            try {
                this.activeSound.upload(title, author, year, tags, path,this.username,"0");
            }
            catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        this.menu.addOption("Logoff", ()->{
            this.activeSound.logOff(this.username);
            authenticationPage();
        });

        this.menu.start();
    }

    private void authenticationPage(){
        Scanner scan = new Scanner(System.in);

        this.menu.clear();

        this.menu.addOption("Login", ()->{
            System.out.print("Username:\n$ ");
            String username = scan.nextLine();
            System.out.print("Password:\n$ ");
            String password = scan.nextLine();

            try{
                this.activeSound.login(username, password, socket);
                System.out.println("Welcome, " + username);
                this.username = username;
                mainPage();
            }
            catch(InvalidPasswordException | UserAlreadyOnlineException | UserNotRegisteredException e){
                System.out.println(e.getMessage());
                this.menu.start();
            }
        });

        this.menu.addOption("Register", ()->{
            System.out.print("Username:\n$ ");
            String username = scan.nextLine();
            System.out.print("Password:\n$ ");
            String password = scan.nextLine();

            try{
                this.activeSound.register(username, password);
                System.out.println(username + " has been registered");
            }
            catch(UserAlreadyRegisteredException e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        this.menu.addOption("Exit", "exit;color-red;", ()->this.activeSound.exit());

        this.menu.start();
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("127.0.0.1", 25567);
            Client client = new Client(s);

            client.authenticationPage();
        }
        catch(Exception e){
            System.out.println("Server offline");
        }
    }
}