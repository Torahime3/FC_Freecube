package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.pvp.PvpArea;
import org.bukkit.Location;

import java.io.IOException;

public class PvpAreaTypeAdapter extends TypeAdapter<PvpArea> {

    @Override
    public void write(JsonWriter out, PvpArea value) throws IOException {
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
        out.name("meleeWeapon").value(value.isMeleeWeapon());
        out.name("rangeWeapon").value(value.isRangeWeapon());
        out.endObject();
    }

    @Override
    public PvpArea read(JsonReader in) throws IOException{
        in.beginObject();
        double a_x = 0;
        double a_y = 0;
        double a_z = 0;
        double b_x = 0;
        double b_y = 0;
        double b_z = 0;
        boolean meleeWeapon = false;
        boolean rangeWeapon = false;
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
                                while (in.hasNext()) {
                                    String locationAName = in.nextName();
                                    switch (locationAName) {
                                        case "x":
                                            a_x = in.nextDouble();
                                            break;
                                        case "y":
                                            a_y = in.nextDouble();
                                            break;
                                        case "z":
                                            a_z = in.nextDouble();
                                            break;
                                    }
                                }
                                in.endObject();
                                break;
                            case "locationB":
                                in.beginObject();
                                while (in.hasNext()) {
                                    String locationBName = in.nextName();
                                    switch (locationBName) {
                                        case "x":
                                            b_x = in.nextDouble();
                                            break;
                                        case "y":
                                            b_y = in.nextDouble();
                                            break;
                                        case "z":
                                            b_z = in.nextDouble();
                                            break;
                                    }
                                }
                                in.endObject();
                                break;
                        }
                    }
                    in.endObject();
                    break;
                case "meleeWeapon":
                    meleeWeapon = in.nextBoolean();
                    break;
                case "rangeWeapon":
                    rangeWeapon = in.nextBoolean();
                    break;
            }
        }
        in.endObject();
        PvpArea pa = new PvpArea(new Location(null, a_x, a_y, a_z), new Location(null, b_x, b_y, b_z));
        pa.setMeleeWeapon(meleeWeapon);
        pa.setRangeWeapon(rangeWeapon);
        return pa;
    }
}
