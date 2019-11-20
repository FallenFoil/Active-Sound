package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static void authentication(PrintWriter out){
        Menu menu = new Menu("ActiveSound");

        menu.addOption("Login", new Menu.CallBack() {
            @Override
            public void run() {
                System.out.print("Username:\n$ ");
                Scanner scan = new Scanner(System.in);
                String username = scan.nextLine();
                System.out.print("Password:\n$ ");
                String password = scan.nextLine();

                String str = "0|" + username + "|" + password;

                out.println(str);
                out.flush();
            }
        });

        menu.addOption("Register", new Menu.CallBack() {
            @Override
            public void run() {
                System.out.println("You have choosen the Register option");
            }
        });

        menu.addOption("Exit", new Menu.CallBack() {
            @Override
            public void run() {
                System.out.println("You have choosen the Exit option");
            }
        });

        menu.start();
    }

    public static void main(String[] args) throws IOException{
        Socket s = new Socket("127.0.0.1", 25567);

        //BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());

        authentication(out);

        s.shutdownOutput();
        //s.shutdownInput();
        s.close();
    }
}
