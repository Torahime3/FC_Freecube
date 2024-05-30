package fr.torahime.freecube.services.gameplayers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.services.IService;
import fr.torahime.freecube.services.Service;
import fr.torahime.freecube.services.gameplayers.adapters.PlotIDTypeAdapter;
import fr.torahime.freecube.services.gameplayers.adapters.UUIDTypeAdapter;
import fr.torahime.freecube.utils.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.UUID;

public class GamePlayerService extends Service implements IService<GamePlayer> {

    HttpClient client;
    Gson gson;

    public GamePlayerService(){
        super("Freecube | GamePlayerService");
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
                .registerTypeAdapter(new TypeToken<ArrayList<Integer>>(){}.getType(), new PlotIDTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String convertToJson(GamePlayer object) {
        return gson.toJson(object);
    }

    @Override
    public boolean create(GamePlayer object) {
        String json = convertToJson(object);
        String url = String.format("%s/api/v1/gameplayers", Dotenv.get("BASE_API_URL"));
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(json), "POST", 201);
    }

    @Override
    public boolean update(GamePlayer object) {
        String json = convertToJson(object);
        String url = String.format("%s/api/v1/gameplayers/%s", Dotenv.get("BASE_API_URL"), object.getUuid());
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(json), "PUT", 200);
    }

    @Override
    public boolean delete(GamePlayer object) {
        String url = String.format("%s/api/v1/gameplayers/%s", Dotenv.get("BASE_API_URL"), object.getUuid());
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(""), "DELETE", 200);
    }

    @Override
    public GamePlayer get(String id) {
        String url = String.format("%s/api/v1/gameplayers/%s", Dotenv.get("BASE_API_URL"), id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                JsonElement jsonElement = JsonParser.parseString(response.body());
                System.out.println(jsonElement);
                return gson.fromJson(jsonElement, GamePlayer.class);
            }
        } catch (Exception e) {
            logger.severe("HTTP request failed -> " + e.getMessage());
        }
        return null;
    }
}
