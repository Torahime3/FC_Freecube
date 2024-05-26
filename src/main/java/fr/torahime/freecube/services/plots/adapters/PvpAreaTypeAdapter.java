package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.pvp.PvpArea;

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
    public PvpArea read(JsonReader in) throws IOException {
        return null;
    }
}
