package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.musics.Music;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class MusicTransmittersTypeAdapter extends TypeAdapter<MusicTransmitter> {
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
        String musicName = null;
        Location location = null;
        double x = 0, y = 0, z = 0;
        float volume = 0;
        float pitch = 0;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "musicName":
                    musicName = in.nextString();
                    break;
                case "location":
                    in.beginObject();
                    while (in.hasNext()) {
                        String locName = in.nextName();
                        switch (locName) {
                            case "x":
                                x = in.nextDouble();
                                break;
                            case "y":
                                y = in.nextDouble();
                                break;
                            case "z":
                                z = in.nextDouble();
                                break;
                        }
                    }
                    in.endObject();
                    location = new Location(Bukkit.getWorld("freecube"), x, y, z);
                    break;
                case "volume":
                    volume = (float) in.nextDouble();
                    break;
                case "pitch":
                    pitch = (float) in.nextDouble();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        Music music = Music.getMusicFromName(musicName);


        return new MusicTransmitter(music, location, volume, pitch);
    }
}
