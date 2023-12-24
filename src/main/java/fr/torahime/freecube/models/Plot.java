package fr.torahime.freecube.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Plot {

    private static HashMap<Integer, Plot> plots = new HashMap<>();
    private int id;
    private UUID owner;
    private ArrayList<UUID> members = new ArrayList<>();

    public Plot(int id, UUID owner) {
        this.id = id;
        this.owner = owner;
    }

    public static void claimPlot(int id, UUID owner) {
        plots.put(id, new Plot(id, owner));
    }

    public static Plot getPlot(int id) {
        return plots.get(id);
    }

    public int getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public ArrayList<UUID> getMembers() {
        return members;
    }
}
