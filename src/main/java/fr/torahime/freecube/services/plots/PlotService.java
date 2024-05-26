package fr.torahime.freecube.services.plots;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.interactions.InteractionsMap;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.preferences.PreferencesMap;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.services.plots.adapters.*;
import fr.torahime.freecube.utils.Dotenv;
import org.bukkit.Location;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;

public class PlotService {

    HttpClient client;
    Dotenv dotenv;

    public PlotService() {
        this.client = HttpClient.newHttpClient();
        this.dotenv = new Dotenv();
        dotenv.load();
    }

    public String convertPlotToJson(Plot plot){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Hours.class, new HoursTypeAdapter())
                .registerTypeAdapter(Location.class, new SpawnTypeAdapter())
                .registerTypeAdapter(MusicTransmitter.class, new MusicTransmitterTypeAdapter())
                .registerTypeAdapter(EntityGenerator.class, new EntityGeneratorTypeAdapter())
                .registerTypeAdapter(PvpArea.class, new PvpAreaTypeAdapter())
                .registerTypeAdapter(PreferencesMap.class, new PreferencesTypeAdapter())
                .registerTypeAdapter(InteractionsMap.class, new InteractionsTypeAdapter())
                .registerTypeAdapter(new TypeToken<HashMap<UUID, PlotRoles>>(){}.getType(), new MembersTypeAdapter())
                .setPrettyPrinting()
                .create();
        return gson.toJson(plot);
    }

    public boolean createPlot(Plot plot) {
        String json = convertPlotToJson(plot);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%sapi/v1/plots", dotenv.get("BASE_API_URL"))))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void savePlot(Plot plot){

        String json = convertPlotToJson(plot);
    }
}
