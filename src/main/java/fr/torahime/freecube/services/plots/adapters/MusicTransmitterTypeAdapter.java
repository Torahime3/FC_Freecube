package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.musics.MusicTransmitter;

import java.io.IOException;

public class MusicTransmitterTypeAdapter extends TypeAdapter<MusicTransmitter> {
    @Override
    public void write(JsonWriter out, MusicTransmitter value) throws IOException {
        out.beginObject();
        out.name("musicName").value(value.getMusic().getName());
        out.name("location").beginObject();
        out.name("x").value(Math.round(value.getLocation().getX()));
        out.name("y").value(Math.round(value.getLocation().getY()));
        out.name("z").value(Math.round(value.getLocation().getZ()));
        out.endObject();
        out.name("volume").value(value.getVolume());
        out.name("pitch").value(value.getPitch());
        out.endObject();
    }

    @Override
    public MusicTransmitter read(JsonReader in) throws IOException {
        return null;
    }
}
