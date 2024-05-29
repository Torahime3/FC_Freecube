package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.models.interactions.Interaction;
import fr.torahime.freecube.models.interactions.InteractionsMap;

import java.io.IOException;
import java.util.Map;

public class InteractionsTypeAdapter extends TypeAdapter<InteractionsMap> {

    @Override
    public void write(JsonWriter out, InteractionsMap value) throws IOException {
        out.beginObject();
        out.name("disabledInteractions").beginArray();
        for (Map.Entry<Interaction, PlotStates> entry : value.entrySet()) {
            if (entry.getValue() == PlotStates.DEACTIVATE) {
                out.value(entry.getKey().toString());
            }
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public InteractionsMap read(JsonReader in) throws IOException {
        InteractionsMap interactionsMap = new InteractionsMap();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("disabledInteractions")) {
                in.beginArray();
                while (in.hasNext()) {
                    Interaction interaction = Interaction.valueOf(in.nextString());
                    interactionsMap.put(interaction, PlotStates.DEACTIVATE);
                }
                in.endArray();
            }
        }
        in.endObject();
        return interactionsMap;
    }

}
