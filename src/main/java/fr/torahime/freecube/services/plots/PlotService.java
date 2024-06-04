package fr.torahime.freecube.services.plots;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.interactions.InteractionsMap;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.preferences.PreferencesMap;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.services.IService;
import fr.torahime.freecube.services.Service;
import fr.torahime.freecube.services.plots.adapters.*;
import fr.torahime.freecube.utils.Dotenv;
import org.bukkit.Location;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;

public class PlotService extends Service implements IService<Plot> {

    HttpClient client;
    Gson gson;

    public PlotService() {
        super("Freecube | PlotService");
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Hours.class, new HoursTypeAdapter())
                .registerTypeAdapter(Location.class, new SpawnTypeAdapter())
                .registerTypeAdapter(MusicTransmitter.class, new MusicTransmittersTypeAdapter())
                .registerTypeAdapter(EntityGenerator.class, new EntityGeneratorTypeAdapter())
                .registerTypeAdapter(PvpArea.class, new PvpAreaTypeAdapter())
                .registerTypeAdapter(PreferencesMap.class, new PreferencesTypeAdapter())
                .registerTypeAdapter(InteractionsMap.class, new InteractionsTypeAdapter())
                .registerTypeAdapter(new TypeToken<HashMap<UUID, PlotRoles>>(){}.getType(), new MembersTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String convertToJson(Plot object) {
        return gson.toJson(object);
    }

    @Override
    public boolean create(Plot object) {
        String json = convertToJson(object);
        String url = String.format("%s/api/v1/plots", Dotenv.get("BASE_API_URL"));
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(json), "POST", 201);
    }

    @Override
    public boolean update(Plot object) {
        String json = convertToJson(object);
        String url = String.format("%s/api/v1/plots/%s", Dotenv.get("BASE_API_URL"), object.getId());
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(json), "PUT", 201);
    }

    @Override
    public boolean delete(Plot object) {
        String url = String.format("%s/api/v1/plots/%s", Dotenv.get("BASE_API_URL"), object.getId());
        return sendRequest(url, HttpRequest.BodyPublishers.ofString(""), "DELETE", 200);
    }

    @Override
    public Plot get(String id) {
        String url = String.format("%s/api/v1/plots/%s", Dotenv.get("BASE_API_URL"), id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                JsonElement jsonElement = JsonParser.parseString(response.body());
                return gson.fromJson(jsonElement, Plot.class);
            }
        } catch (Exception e) {
            logger.severe("HTTP request failed -> " + e.getMessage());
        }
        return null;
    }
}
