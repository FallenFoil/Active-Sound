package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Menu authentication(PrintWriter out){
        Menu menu = new Menu("ActiveSound");

        menu.addOption("Login", ()->{
            System.out.print("Username:\n$ ");
            Scanner scan = new Scanner(System.in);
            String username = scan.nextLine();
            System.out.print("Password:\n$ ");
            String password = scan.nextLine();

            String str = "0|" + username + "|" + password;

            out.println(str);
            out.flush();
        });

        menu.addOption("Register", ()->{
            System.out.println("You have choosen the Register option");
        });

        menu.addOption("Exit", "exit;color=150,0,0;", ()->{
            out.println("quit");
            out.flush();
        });

        return menu;
    }

    private static Menu mainMenu(PrintWriter out){
        Menu menu = new Menu("ActiveSound");

        menu.addOption("Exit", "exit;color=150,0,0;", ()->{
            System.out.println("Ola Cesar");
        });

        return menu;
    }

    private static Thread read(Socket s){
        return new Thread(()->{
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String str = in.readLine();

                while(!str.equals("shutdown")){
                    System.out.println(str);
                    String[] args = str.split("[|]");
                    switch (args[0]){
                        case "0":


                            break;
                        default:
                            break;
                    }
                    str = in.readLine();
                }

                System.out.println("Shutting Down");

                s.shutdownOutput();
                s.shutdownInput();
                s.close();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        });
    }

    private static Thread write(Socket s){
        return new Thread(()->{
            try{
                PrintWriter out = new PrintWriter(s.getOutputStream());
                authentication(out).start();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        });
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("127.0.0.1", 25567);
            read(s).start();
            write(s).start();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
