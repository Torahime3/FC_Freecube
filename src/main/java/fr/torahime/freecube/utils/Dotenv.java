package fr.torahime.freecube.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class Dotenv {

    private static HashMap<String, String> envEntries = new HashMap<>();

    public static void load(){
        Logger logger = Logger.getLogger("Freecube | Dotenv");
        try{
            File file = new File("../.env");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                envEntries.put(line.split("=")[0], line.split("=")[1]);
            }
            logger.info("Dotenv loaded successfully");
        } catch (Exception e){
            logger.warning("Error while loading dotenv file : " + e.getMessage());
        }
    }

    public static String get(String key){

        if(System.getenv(key) == null){
            String value = envEntries.getOrDefault(key, null);
            if (value != null){
                return value.replace("\"", "");
            }
            return null;
        } else {
            return System.getenv(key);
        }

    }

}
