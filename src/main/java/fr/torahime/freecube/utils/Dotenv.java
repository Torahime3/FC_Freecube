package fr.torahime.freecube.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Dotenv {

    private HashMap<String, String> envEntries = new HashMap<>();

    public void load(){
        try{
            File file = new File(".env");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                envEntries.put(line.split("=")[0], line.split("=")[1]);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){

        if(System.getenv(key) == null){
            return envEntries.get(key).replace("\"", "");
        } else {
            return System.getenv(key);
        }

    }

}
