package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static void authentication(PrintWriter out){
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

        menu.addOption("Exit", ()->{
            out.println("quit");
            out.flush();
        });

        menu.start();
    }

    private static Menu mainMenu(){
        Menu menu = new Menu("ActiveSound");

        menu.addOption("Exit", ()->{
            System.out.println("Ola Cesar");
        });

        return menu;
    }

    private static Thread in(Socket s){
        return new Thread(()->{
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String str = in.readLine();

                while(!str.equals("shutdown")){
                    System.out.println(str);
                    String[] args = str.split("[|]");
                    switch (args[0]){
                        case "0":
                            String[] responseArgs = args[1].split("=");
                            boolean response = Boolean.parseBoolean(responseArgs[0]);
                            System.out.println(responseArgs[1]);
                            mainMenu().start();
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

    private static Thread out(Socket s){
        return new Thread(()->{
            try{
                PrintWriter out = new PrintWriter(s.getOutputStream());
                authentication(out);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        });
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("127.0.0.1", 25567);
            in(s).start();
            out(s).start();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
