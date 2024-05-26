package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Location;

import java.io.IOException;

public class SpawnTypeAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter out, Location value) throws IOException {
        out.beginObject();
        out.name("x").value(value.getX());
        out.name("y").value(value.getY());
        out.name("z").value(value.getZ());
        out.endObject();
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        return null;
    }
}
