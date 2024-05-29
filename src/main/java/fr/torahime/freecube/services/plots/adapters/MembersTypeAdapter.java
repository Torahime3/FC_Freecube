package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.roles.PlotRoles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MembersTypeAdapter extends TypeAdapter<HashMap<UUID, PlotRoles>> {

    @Override
    public void write(JsonWriter out, HashMap<UUID, PlotRoles> value) throws IOException {
        out.beginArray();
        for (Map.Entry<UUID, PlotRoles> entry : value.entrySet()) {
            out.beginObject();
            out.name("uuid").value(entry.getKey().toString());
            out.name("role").value(entry.getValue().toString());
            out.endObject();
        }
        out.endArray();
    }

    @Override
    public HashMap<UUID, PlotRoles> read(JsonReader in) throws IOException {
        HashMap<UUID, PlotRoles> members = new HashMap<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            UUID uuid = null;
            PlotRoles role = null;
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "uuid":
                        uuid = UUID.fromString(in.nextString());
                        break;
                    case "role":
                        role = PlotRoles.valueOf(in.nextString());
                        break;
                }
            }
            in.endObject();
            members.put(uuid, role);
        }
        in.endArray();
        return members;
    }
}
