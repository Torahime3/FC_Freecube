package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.hours.Hours;

import java.io.IOException;

public class HoursTypeAdapter extends TypeAdapter<Hours> {

    @Override
    public void write(JsonWriter out, Hours value) throws IOException {
        out.beginObject();
        out.name("ticks").value(value.getTick());
        out.endObject();
    }

    @Override
    public Hours read(JsonReader in) throws IOException {
        in.beginObject();
        int ticks = 0;
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "ticks":
                    ticks = in.nextInt();
                    break;
            }
        }
        in.endObject();
        return Hours.getHourFromTick(ticks);
    }

}
