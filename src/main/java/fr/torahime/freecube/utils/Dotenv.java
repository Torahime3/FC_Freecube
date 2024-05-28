package fr.torahime.freecube.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class Dotenv {

    private static HashMap<String, String> envEntries = new HashMap<>();

    public static void load(Logger logger){
        try{
            InputStream file = Dotenv.class.getResourceAsStream("/.env");
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
            return envEntries.get(key).replace("\"", "");
        } else {
            return System.getenv(key);
        }

    }

}
