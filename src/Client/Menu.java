package Client;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu{
    private String name;
    private int nOptions;
    private ArrayList<CallBack> callBacks;
    private ArrayList<String> options;

    public Menu(){
        this.nOptions = 0;
        this.callBacks = new ArrayList<>();
        this.options = new ArrayList<>();
    }

    public Menu(String newName){
        this.name = newName;
        this.nOptions = 0;
        this.callBacks = new ArrayList<>();
        this.options = new ArrayList<>();
    }

    public void start(String newName){
        //Header
        System.out.print("*");
        for(int i=0; i<newName.length()*3; i++){
            System.out.print("*");
        }
        System.out.println("*");

        System.out.print("*");
        for(int i=0; i<newName.length(); i++){
            System.out.print(" ");
        }
        System.out.print(newName);
        for(int i=0; i<newName.length(); i++){
            System.out.print(" ");
        }
        System.out.println("*");

        System.out.print("*");
        for(int i=0; i<newName.length()*3; i++){
            System.out.print("*");
        }
        System.out.println("*");

        //Boddy
        for(int w=0; w<this.options.size(); w++){
            System.out.print("  " + w+1 + ")");
            for(int i=0; i< 5; i++){
                System.out.print(" ");
            }
            if(this.nOptions/10 < 1 ){
                System.out.print(" ");
            }

            System.out.print(this.options.get(w));

            System.out.print("\n");
        }
    }

    public void start(){
        //Header
        System.out.print("*");
        for(int i=0; i<name.length()*3; i++){
            System.out.print("*");
        }
        System.out.println("*");

        System.out.print("*");
        for(int i=0; i<name.length(); i++){
            System.out.print(" ");
        }
        System.out.print(name);
        for(int i=0; i<name.length(); i++){
            System.out.print(" ");
        }
        System.out.println("*");

        System.out.print("*");
        for(int i=0; i<name.length()*3; i++){
            System.out.print("*");
        }
        System.out.println("*");

        //Boddy
        for(int w=0; w<this.options.size(); w++){
            System.out.print("  " + w + ")");
            for(int i=0; i< 5; i++){
                System.out.print(" ");
            }
            if(this.nOptions/10 < 1 ){
                System.out.print(" ");
            }

            System.out.print(this.options.get(w));

            System.out.print("\n");
        }

        //Scanner
        System.out.print("$ ");

        Scanner in = new Scanner(System.in);
        int op = -1;

        try {
            op = in.nextInt();
            this.callBacks.get(op).run();
        }
        catch (NumberFormatException | InputMismatchException e){
            System.out.println("Input Inválido");
        }
    }


    public void addOption(String name, CallBack callBack){
        this.nOptions++;
        this.callBacks.add(callBack);
        this.options.add(name);
    }


    public interface CallBack {
        public void run();
    }
}
