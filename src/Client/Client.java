package Client;

import Data.InvalidPasswordException;
import Data.UserAlreadyOnlineException;
import Data.UserAlreadyRegisteredException;
import Data.UserNotRegisteredException;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client{
    private RemoteActiveSound activeSound;
    private Menu menu;
    private String id;

    private Client(Socket s){
        this.activeSound = new RemoteActiveSound(s);
        this.menu = new Menu("ActiveSound");
        this.id = null;
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
        menu.clear();

        menu.addOption("Next Page", ()->{
            System.out.println("Next Page");
            this.menu.start();
        });

        menu.addOption("Previous Page", ()->{
            System.out.println("Previous Page");
            this.menu.start();
        });

        menu.addOption("Download", ()->{
            int id = validIntegerInput("Music's id:\n$ ", scan, -2);

            try {
                this.activeSound.download(id);
            }
            catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        menu.addOption("Back", this::mainPage);

        this.menu.start();
    }

    private void mainPage(){
        Scanner scan = new Scanner(System.in);
        this.menu.clear();

        this.menu.addOption("Search", ()->{
            System.out.print("Music tags (separated by '|'):\n$ ");
            String tags = scan.nextLine();
            searchPage(this.activeSound.search(tags));
        });

        this.menu.addOption("Download", ()->{
            int id = validIntegerInput("Music's id:\n$ ", scan, -2);

            try {
                this.activeSound.download(id);
            }
            catch (FileNotFoundException e){
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
                this.activeSound.upload(title, author, year, tags, path);
            }
            catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }

            this.menu.start();
        });

        this.menu.addOption("Logoff", ()->{
            this.activeSound.logOff(this.id);
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
                this.activeSound.login(username, password);
                System.out.println("Welcome, " + username);
                this.id = username;
                mainPage();
            }
            catch(InvalidPasswordException | UserAlreadyOnlineException | UserNotRegisteredException e){
                System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }
}
