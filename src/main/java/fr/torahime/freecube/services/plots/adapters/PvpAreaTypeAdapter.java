package fr.torahime.freecube.services.plots.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.torahime.freecube.models.areamaker.LocationType;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.pvp.PvpWeapons;
import org.bukkit.Location;

import java.io.IOException;

public class PvpAreaTypeAdapter extends TypeAdapter<PvpArea> {

    @Override
    public void write(JsonWriter out, PvpArea value) throws IOException {
        if (!value.isValid()) return;

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

        out.name("weapons").beginObject();
        out.name("swords").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.SWORDS)));
        out.name("shovels").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.SHOVELS)));
        out.name("pickaxes").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.PICKAXES)));
        out.name("axes").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.AXES)));
        out.name("bows").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.BOWS)));
        out.name("crossbows").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.CROSSBOWS)));
        out.name("tridents").value(PlotStates.mapStateToLiteralString(value.getPvpWeaponsMap().get(PvpWeapons.TRIDENTS)));
        out.endObject();

        out.endObject();
    }

    @Override
    public PvpArea read(JsonReader in) throws IOException{
        PvpArea pvpArea = new PvpArea();

        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            if (key.equals("area")) {
                in.beginObject();
                Location locationA = new Location(null, 0, 0, 0);
                Location locationB = new Location(null, 0, 0, 0);
                while (in.hasNext()) {
                    String locationKey = in.nextName();
                    if (locationKey.equals("locationA")) {
                        in.beginObject();
                        while (in.hasNext()) {
                            String locationAKey = in.nextName();
                            switch (locationAKey) {
                                case "x":
                                    locationA.setX(in.nextDouble());
                                    break;
                                case "y":
                                    locationA.setY(in.nextDouble());
                                    break;
                                case "z":
                                    locationA.setZ(in.nextDouble());
                                    break;
                            }
                        }
                        in.endObject();
                    } else if (locationKey.equals("locationB")) {
                        in.beginObject();
                        while (in.hasNext()) {
                            String locationBKey = in.nextName();
                            switch (locationBKey) {
                                case "x":
                                    locationB.setX(in.nextDouble());
                                    break;
                                case "y":
                                    locationB.setY(in.nextDouble());
                                    break;
                                case "z":
                                    locationB.setZ(in.nextDouble());
                                    break;
                            }
                        }
                        in.endObject();
                    }
                }
                in.endObject();
                pvpArea.setLocationA(locationA);
                pvpArea.setLocationB(locationB);
            } else if (key.equals("weapons")) {
                in.beginObject();
                while (in.hasNext()) {
                    String weaponKey = in.nextName();
                    PlotStates state = PlotStates.fromLiteralString(in.nextString());
                    switch (weaponKey) {
                        case "swords":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.SWORDS, state);
                            break;
                        case "shovels":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.SHOVELS, state);
                            break;
                        case "pickaxes":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.PICKAXES, state);
                            break;
                        case "axes":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.AXES, state);
                            break;
                        case "bows":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.BOWS, state);
                            break;
                        case "crossbows":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.CROSSBOWS, state);
                            break;
                        case "tridents":
                            pvpArea.getPvpWeaponsMap().put(PvpWeapons.TRIDENTS, state);
                            break;
                    }
                }
                in.endObject();
            }

        }
        in.endObject();
        return pvpArea;
    }
}
