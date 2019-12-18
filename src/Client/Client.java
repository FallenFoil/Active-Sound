package Client;

import Data.InvalidPasswordException;
import Data.UserAlreadyOnlineException;
import Data.UserAlreadyRegisteredException;
import Data.UserNotRegisteredException;

import java.net.Socket;
import java.util.Scanner;

public class Client{
    private static Menu mainPage(Menu menu){
        menu.clear();

        menu.addOption("Search", ()->{
            System.out.println("Search");
        });

        menu.addOption("Download", ()->{
            System.out.println("Download");

        });

        menu.addOption("Upload", ()->{
            System.out.println("Upload");
        });

        menu.addOption("Logoff", ()->{
            System.out.println("Logoof");
        });

        return menu;
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("127.0.0.1", 25567);
            RemoteActiveSound activeSound = new RemoteActiveSound(s);

            Menu menu = new Menu("ActiveSound");
            Scanner scan = new Scanner(System.in);

            menu.addOption("Login", ()->{
                System.out.print("Username:\n$ ");
                String username = scan.nextLine();
                System.out.print("Password:\n$ ");
                String password = scan.nextLine();

                try{
                    activeSound.login(username, password,s);
                    System.out.println("Welcome, " + username);
                }
                catch(InvalidPasswordException | UserAlreadyOnlineException | UserNotRegisteredException e){
                    System.out.println(e.getMessage());
                }

                mainPage(menu).start();
            });

            menu.addOption("Register", ()->{
                System.out.print("Username:\n$ ");
                String username = scan.nextLine();
                System.out.print("Password:\n$ ");
                String password = scan.nextLine();

                try{
                    activeSound.register(username, password);
                    System.out.println(username + " has been registered");
                }
                catch(UserAlreadyRegisteredException e){
                    System.out.println(e.getMessage());
                }

                menu.start();
            });

            menu.addOption("Exit", "exit;color-red;", ()->{
                activeSound.exit();
            });

            menu.start();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
