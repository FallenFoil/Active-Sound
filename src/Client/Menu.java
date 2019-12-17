package Client;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu{

    private String name;
    private int nOptions;
    private Map<String, String> allSettings; //All possible settings
    private Map<Integer, Trio> options;
    private List<String> data;

    public Menu(){
        this.nOptions = 1;
        this.allSettings = new HashMap<>();
        this.options = new HashMap<>();
        this.data = new ArrayList<>();

        populateAllSettings();
    }

    public Menu(String newName){
        this.name = newName;
        this.nOptions = 1;
        this.options = new HashMap<>();
        this.allSettings = new HashMap<>();
        this.data = new ArrayList<>();

        populateAllSettings();
    }

    private void populateAllSettings(){
        this.allSettings.put("reset", "\u001B[0m");

        this.allSettings.put("bold", "\u001B[1m");
        this.allSettings.put("italic", "\u001B[3m");
        this.allSettings.put("underline", "\u001B[4m");
        this.allSettings.put("reverse", "\u001B[7m");
        this.allSettings.put("crossed-out", "\u001B[9m");
        this.allSettings.put("double-underline", "\u001B[21m");

        this.allSettings.put("color-white", "\u001B[30m");
        this.allSettings.put("color-red", "\u001B[31m");
        this.allSettings.put("color-lime", "\u001B[32m");
        this.allSettings.put("color-gold", "\u001B[33m");
        this.allSettings.put("color-blue", "\u001B[34m");
        this.allSettings.put("color-eggplant", "\u001B[35m");
        this.allSettings.put("color-persiangreen", "\u001B[36m");
        this.allSettings.put("color-gray", "\u001B[37m");
        this.allSettings.put("color-default", "\u001B[39m");

        this.allSettings.put("background-color-white", "\u001B[40m");
        this.allSettings.put("background-color-red", "\u001B[41m");
        this.allSettings.put("background-color-lime", "\u001B[42m");
        this.allSettings.put("background-color-gold", "\u001B[43m");
        this.allSettings.put("background-color-blue", "\u001B[44m");
        this.allSettings.put("background-color-eggplant", "\u001B[45m");
        this.allSettings.put("background-color-persiangreen", "\u001B[46m");
        this.allSettings.put("background-color-gray", "\u001B[47m");
        this.allSettings.put("background-color-default", "\u001B[49m");

        this.allSettings.put("framed", "\u001B[51m");
    }

    public void start(String newName){
        //Header
        String asterisks = "*".repeat(Math.max(0, newName.length() * 3));
        String spaces = " ".repeat(Math.max(0, newName.length()));
        String header = "*" + asterisks + "*\n" +
                "*" + spaces + newName + spaces + "*\n" +
                "*" + asterisks + "*\n";

        //Body
        StringBuilder body = new StringBuilder();
        for(String str : this.data){
            body.append(str).append("\n");
        }

        for(int j=1; j<=this.options.size(); j++){
            if(this.options.containsKey(j)){
                Trio trio = this.options.get(j);
                body.append("  ");
                if(trio.getSettings() != null){
                    for(String str : trio.getSettings()){
                        if(!str.equals("exit")){
                            body.append(str);
                        }
                    }
                }
                body.append(j).append(")     ").append(trio.getName()).append("\u001B[0m\n");
            }
        }

        if(this.options.containsKey(0)){
            Trio trio = this.options.get(0);
            body.append("  ");
            for(String str : trio.getSettings()){
                if(!str.equals("exit")){
                    body.append(str);
                }
            }
            body.append("0)     ").append(trio.getName()).append("\u001B[0m\n");
        }

        System.out.print(header + body.toString() + "$ ");

        //Scanner
        Scanner in = new Scanner(System.in);
        int op;

        try {
            op = in.nextInt();
            while(!this.options.containsKey(op)){
                System.out.print("That option doesn't exists!\n$ ");
                op = in.nextInt();
            }

            this.options.get(op).getCallback().run();
        }
        catch (NumberFormatException | InputMismatchException e){
            System.out.println("Input Inválido");
        }
    }

    public void start(){
        //Header
        String asterisks = "*".repeat(Math.max(0, this.name.length() * 3));
        String spaces = " ".repeat(Math.max(0, this.name.length()));

        String header = "*" + asterisks + "*\n" +
                "*" + spaces + name + spaces + "*\n" +
                "*" + asterisks + "*\n";

        //Body
        StringBuilder body = new StringBuilder();
        for(String str : this.data){
            body.append(str).append("\n");
        }

        for(int j=1; j<=this.options.size(); j++){
            if(this.options.containsKey(j)){
                Trio trio = this.options.get(j);
                body.append("  ");
                if(trio.getSettings() != null){
                    for(String str : trio.getSettings()){
                        if(!str.equals("exit")){
                            body.append(str);
                        }
                    }
                }
                body.append(j).append(")     ").append(trio.getName()).append("\u001B[0m\n");
            }
        }

        if(this.options.containsKey(0)){
            Trio trio = this.options.get(0);
            body.append("  ");
            for(String str : trio.getSettings()){
                if(!str.equals("exit")){
                    body.append(str);
                }
            }
            body.append("0)     ").append(trio.getName()).append("\u001B[0m\n");
        }

        System.out.print(header + body.toString() + "$ ");

        //Scanner
        Scanner in = new Scanner(System.in);
        int op;

        try {
            op = in.nextInt();
            while(!this.options.containsKey(op)){
                System.out.print("That option doesn't exists!\n$ ");
                op = in.nextInt();
            }

            this.options.get(op).getCallback().run();
        }
        catch (NumberFormatException | InputMismatchException e){
            System.out.println("Input Inválido");
        }
    }

    private List<String> parseOptions(String settings){
        String[] args = settings.split("[;]");
        List<String> list = new ArrayList<>();

        for(String arg : args) {
            String[] moreArgs = arg.split("[=]");
            switch (moreArgs[0].toLowerCase().replaceAll("\\s+", "")) {
                case "exit":
                    list.add("exit");
                    break;
                case "color":
                    String[] rgb = moreArgs[1].replaceAll("\\s+", "").split("[,]");
                    list.add("\u001B[38;2;" + rgb[0] + ";" + rgb[1] + ";" + rgb[2] + "m");
                    break;
                case "background-color":
                    String[] back_rgb = moreArgs[1].replaceAll("\\s+", "").split("[,]");
                    list.add("\u001B[48;2;" + back_rgb[0] + ";" + back_rgb[1] + ";" + back_rgb[2] + "m");
                    break;
                default:
                    list.add(this.allSettings.get(moreArgs[0].toLowerCase().replaceAll("\\s+", "")));
                    break;
            }
        }

        return list;
    }

    public void addOption(String name, CallBack callBack){
        Trio trio = new Trio(name, null, callBack);
        this.options.put(this.nOptions, trio);
        this.nOptions++;
    }

    public void addOption(String name, String settings, CallBack callBack){
        List<String> map = parseOptions(settings);
        Trio trio;

        if(map.contains("exit")){
            trio = new Trio(name, map, callBack);
            this.options.put(0, trio);
        }
        else{
            trio = new Trio(name, map, callBack);
            this.options.put(this.nOptions, trio);
            this.nOptions++;
        }
    }

    public void addData(String newData){
        this.data.add(newData);
    }

    public void clear(){
        this.nOptions = 1;
        this.options.clear();
    }

    public interface CallBack {
        void run();
    }

    static class Trio{
        private final String name;
        private final List<String> settings;
        private final CallBack callback;

        Trio(String newName, List<String> newSettings, CallBack newCallbacks){
            this.name = newName;
            this.settings = newSettings;
            this.callback = newCallbacks;
        }

        String getName(){
            return this.name;
        }

        List<String> getSettings(){
            return this.settings;
        }

        CallBack getCallback(){
            return this.callback;
        }
    }
}

