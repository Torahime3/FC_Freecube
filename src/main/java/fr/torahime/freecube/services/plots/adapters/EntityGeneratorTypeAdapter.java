package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.entitys.PlotEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class EntityGeneratorTypeAdapter extends TypeAdapter<EntityGenerator> {

    @Override
    public void write(JsonWriter out, EntityGenerator value) throws IOException {
        if(!value.isValid()) return;

        out.beginObject();
        out.name("area").beginObject();
        out.name("locationA").beginObject();
        out.name("x").value(value.getA_X());
        out.name("y").value(value.getA_Y());
        out.name("z").value(value.getA_Z());
        out.endObject();
        out.name("locationB").beginObject();
        out.name("x").value(value.getB_X());
        out.name("y").value(value.getB_Y());
        out.name("z").value(value.getB_Z());
        out.endObject();
        out.endObject();
        out.name("entityName").value(value.getEntity().toString());
        out.endObject();
    }

    @Override
    public EntityGenerator read(JsonReader in) throws IOException {
        Location locationA = null;
        Location locationB = null;
        PlotEntity entity = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "area":
                    in.beginObject();
                    while (in.hasNext()) {
                        String areaName = in.nextName();
                        switch (areaName) {
                            case "locationA":
                                in.beginObject();
                                double x = 0, y = 0, z = 0;
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
                                locationA = new Location(Bukkit.getWorld("freecube"), x, y, z);
                                break;
                            case "locationB":
                                in.beginObject();
                                x = 0; y = 0; z = 0;
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
                                locationB = new Location(Bukkit.getWorld("freecube"), x, y, z);
                                break;
                        }
                    }
                    in.endObject();
                    break;
                case "entityName":
                    entity = PlotEntity.valueOf(in.nextString());
                    break;
            }
        }
        in.endObject();
        return new EntityGenerator(locationA, locationB, entity);
    }

}
