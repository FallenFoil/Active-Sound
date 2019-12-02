package Client;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    //Defines
    private static final int exit = 0;
    private static final int textColor = 1;

    private String name;
    private int nOptions;
    private Map<Integer, String> menuOptions;
    private Map<Integer, Map<Integer, String>> optionSettings;
    private Map<Integer, CallBack> callBacks;

    public Menu(){
        this.nOptions = 1;
        this.menuOptions = new HashMap<>();
        this.optionSettings = new HashMap<>();
        this.callBacks = new HashMap<>();
    }

    public Menu(String newName){
        this.name = newName;
        this.nOptions = 1;
        this.menuOptions = new HashMap<>();
        this.optionSettings = new HashMap<>();
        this.callBacks = new HashMap<>();
    }

    public void start(String newName){
        //Header
        StringBuilder header = new StringBuilder("*");
        for(int i=0; i<name.length()*3; i++){
            header.append("*");
        }
        header.append("*\n*");

        for(int i=0; i<name.length(); i++){
            header.append(" ");
        }
        header.append(newName);
        for(int i=0; i<name.length(); i++){
            header.append(" ");
        }
        header.append("*\n");

        header.append("*");
        for(int i=0; i<name.length()*3; i++){
            header.append("*");
        }
        header.append("*\n");

        //Boddy
        StringBuilder body = new StringBuilder();
        for(int j=1; j<=this.menuOptions.size(); j++){
            int w = j;

            if(!this.menuOptions.containsKey(w)){
                w = 0;
            }

            int optionNumber = w;
            String str = "";
            if(this.optionSettings.containsKey(w)){
                if(this.optionSettings.get(w).containsKey(this.exit)){
                    optionNumber = 0;
                }
                if(this.optionSettings.get(w).containsKey(this.textColor)){
                    str = this.optionSettings.get(w).get(this.textColor);
                }
            }
            body.append("  " + optionNumber + ")");
            for(int i=0; i< 5; i++){
                body.append(" ");
            }
            if(this.nOptions/10 < 1 ){
                body.append(" ");
            }

            body.append(str + this.menuOptions.get(optionNumber) + "\u001B[0m");

            body.append("\n");
        }

        System.out.print(header.toString() + body.toString() + "$ ");

        //Scanner
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

    public void start(){
        //Header
        StringBuilder header = new StringBuilder("*");
        for(int i=0; i<name.length()*3; i++){
            header.append("*");
        }
        header.append("*\n*");

        for(int i=0; i<name.length(); i++){
            header.append(" ");
        }
        header.append(name);
        for(int i=0; i<name.length(); i++){
            header.append(" ");
        }
        header.append("*\n");

        header.append("*");
        for(int i=0; i<name.length()*3; i++){
            header.append("*");
        }
        header.append("*\n");

        //Boddy
        StringBuilder body = new StringBuilder();
        for(int j=1; j<=this.menuOptions.size(); j++){
            int w = j;

            if(!this.menuOptions.containsKey(w)){
                w = 0;
            }

            int optionNumber = w;
            String str = "";
            if(this.optionSettings.containsKey(w)){
                if(this.optionSettings.get(w).containsKey(this.exit)){
                    optionNumber = 0;
                }
                if(this.optionSettings.get(w).containsKey(this.textColor)){
                    str = this.optionSettings.get(w).get(this.textColor);
                }
            }
            body.append("  " + optionNumber + ")");
            for(int i=0; i< 5; i++){
                body.append(" ");
            }
            if(this.nOptions/10 < 1 ){
                body.append(" ");
            }

            body.append(str + this.menuOptions.get(optionNumber) + "\u001B[0m");

            body.append("\n");
        }

        System.out.print(header.toString() + body.toString() + "$ ");

        //Scanner
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

    private Map<Integer, String> parseOptions(String settings){
        String[] args = settings.split("[;]");
        Map<Integer, String> list = new HashMap<>();

        for(int i=0; i<args.length; i++){
            String[] moreArgs = args[i].split("[=]");
            switch (moreArgs[0].toLowerCase().replaceAll("\\s+", "")){
                case "exit":
                    list.put(this.exit, "true");
                    break;
                case "color":
                    String[] rgb = moreArgs[1].replaceAll("\\s+", "").split("[,]");
                    list.put(this.textColor, "\u001B[38;2;" + rgb[0] + ";" + rgb[1] + ";" + rgb[2] + "m");
                    break;
                default:
                    break;
            }
        }

        return list;
    }

    public void addOption(String name, CallBack callBack){
        this.menuOptions.put(this.nOptions, name);
        this.callBacks.put(this.nOptions, callBack);
        this.nOptions++;
    }

    public void addOption(String name, String settings, CallBack callBack){
        Map<Integer, String> map = parseOptions(settings);

        if(map.get(0).equals("true")){
            this.menuOptions.put(0, name);
            this.callBacks.put(0, callBack);
            this.optionSettings.put(0, map);
        }
        else{
            this.menuOptions.put(this.nOptions, name);
            this.callBacks.put(this.nOptions, callBack);
            this.optionSettings.put(this.nOptions, map);
            this.nOptions++;
        }

    }

    public void clear(){
        this.nOptions = 1;
        this.menuOptions.clear();
        this.optionSettings.clear();
        this.callBacks.clear();
    }

    public interface CallBack {
        public void run();
    }
}
