package fr.torahime.freecube.models;

import fr.torahime.freecube.models.roles.PlotRoles;

import java.util.*;

public class Plot {

    private int id;
    private static HashMap<Integer, Plot> plots = new HashMap<>();
    private HashMap<UUID, PlotRoles> members = new HashMap<>();

    public Plot(int id, UUID owner) {
        this.id = id;
        this.addPlayer(owner, PlotRoles.CHIEF);
    }

    public int getId() {
        return id;
    }

    public static void claimPlot(int id, UUID owner) {
        Plot plot = new Plot(id, owner);
        plots.put(id, plot);
    }

    public boolean addPlayer(UUID playerUUID, PlotRoles plotRole) {
        if(isPlayerPresent(playerUUID)) {
            return false;
        }
        members.put(playerUUID, plotRole);
        GamePlayer.getPlayer(playerUUID).addPlot(this);
        return true;
    }

    public boolean isPlayerPresent(UUID playerUUID) {
        return members.containsKey(playerUUID);
    }

    public void removePlayer(UUID playerId){
        members.remove(playerId);
        GamePlayer.getPlayer(playerId).removePlot(this);
    }

    public static Plot getPlot(int id) {
        return plots.get(id);
    }

    public PlotRoles getMemberRole(UUID member) {
        return members.get(member);
    }

    public UUID getOwner() {
        for(UUID player : members.keySet()) {
            if(members.get(player) == PlotRoles.CHIEF) {
                return player;
            }
        }
        return null;
    }

    public Set<UUID> getMembers() {
        return members.keySet();
    }


}
