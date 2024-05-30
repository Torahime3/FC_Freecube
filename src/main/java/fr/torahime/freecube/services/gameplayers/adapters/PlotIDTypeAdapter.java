package fr.torahime.freecube.services.gameplayers.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

public class PlotIDTypeAdapter extends TypeAdapter<ArrayList<Integer>> {

    @Override
    public void write(JsonWriter out, ArrayList<Integer> value) throws IOException {
        out.beginArray();
        for (Integer integer : value) {
            out.value(integer);
        }
        out.endArray();
    }

    @Override
    public ArrayList<Integer> read(JsonReader in) throws IOException {
        ArrayList<Integer> integers = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            integers.add(in.nextInt());
        }
        in.endArray();
        return integers;
    }


}
