package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.models.preferences.Preference;
import fr.torahime.freecube.models.preferences.PreferencesMap;

import java.io.IOException;
import java.util.Map;

public class PreferencesTypeAdapter extends TypeAdapter<PreferencesMap> {

    public String mapStateToLiteralString(PlotStates states){
        return switch (states) {
            case ACTIVATE -> "true";
            case CREATIVE -> "creative";
            case ADVENTURE -> "adventure";
            case SURVIVAL -> "survival";
            default -> "false";
        };
    }

    @Override
    public void write(JsonWriter out, PreferencesMap value) throws IOException {
        out.beginObject();
        for (Map.Entry<Preference, PlotStates> entry : value.entrySet()) {
            String key = entry.getKey().toString().toLowerCase();
            String literalValue = mapStateToLiteralString(entry.getValue());
            out.name(entry.getKey().getCode()).value(literalValue);
        }
        out.endObject();
    }

    @Override
    public PreferencesMap read(JsonReader in) throws IOException {
        return null;
    }

}
