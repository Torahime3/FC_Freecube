package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.game.PlotStates;
import fr.torahime.freecube.models.preferences.Preference;
import fr.torahime.freecube.models.preferences.PreferencesMap;

import java.io.IOException;
import java.util.Map;

public class PreferencesTypeAdapter extends TypeAdapter<PreferencesMap> {

    @Override
    public void write(JsonWriter out, PreferencesMap value) throws IOException {
        out.beginObject();
        for (Map.Entry<Preference, PlotStates> entry : value.entrySet()) {
            String literalValue = PlotStates.mapStateToLiteralString(entry.getValue());
            out.name(entry.getKey().getCode()).value(literalValue);
        }
        out.endObject();
    }

    @Override
    public PreferencesMap read(JsonReader in) throws IOException {
        PreferencesMap preferences = new PreferencesMap();
        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            Preference preference = Preference.fromCode(key);
            PlotStates state = PlotStates.fromLiteralString(in.nextString());
            preferences.put(preference, state);
        }
        in.endObject();
        return preferences;
    }

}
