package fr.torahime.freecube.models;

import fr.torahime.freecube.models.roles.PlotRoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {

    private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();
    private static ArrayList<Plot> plots = new ArrayList<>();
    private UUID uuid;
    private boolean canClaimPlot = true;

    public GamePlayer(UUID uuid){
        this.uuid = uuid;
    }

    public static void addGamePlayer(UUID uuid){
        gamePlayers.put(uuid, new GamePlayer(uuid));
    }

    public static GamePlayer getPlayer(UUID playerUUID) {
        return gamePlayers.get(playerUUID);
    }

    public ArrayList<Plot> getPlots() {
        return plots;
    }

    public int getChefPlotsCount(){
        int count = 0;
        for(Plot plot : plots){
            if(plot.getMemberRole(uuid).equals(PlotRoles.CHIEF)){
                count++;
            }
        }
        return count;
    }

    public void addPlot(Plot plot){
        plots.add(plot);
    }

    public void removePlot(Plot plot){
        plots.remove(plot);
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean canClaimPlot() {
        return canClaimPlot;
    }

    public void setCanClaimPlot(boolean canClaimPlot) {
        this.canClaimPlot = canClaimPlot;
    }


}
