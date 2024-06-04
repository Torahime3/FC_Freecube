package fr.torahime.freecube.utils;

import org.fusesource.jansi.Ansi;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Dotenv class
 * <p>
 * Load .env file and store it in a hashmap
 * <b>The system environment variables have priority over the hashmap</b>
 * If the key is not found in the system environment variables, it will look in the hashmap
 * If the key is found in the hashmap, it will return the value
 * If the key is not found in the hashmap, it will return null
 * </p>
 * @author Torahime
 */
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
            logger.info(ANSIColors.GREEN + "Dotenv loaded successfully" + ANSIColors.RESET);
        } catch (Exception e){
            logger.warning(ANSIColors.RED + "Error while loading dotenv file : " + ANSIColors.RESET + e.getMessage());
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
