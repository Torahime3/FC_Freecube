package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.entitys.EntityGenerator;

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
        out.name("entityName").value(value.getEntity().toString());
        out.endObject();
        out.endObject();
    }

    @Override
    public EntityGenerator read(JsonReader in) throws IOException {
        return null;
    }

}
