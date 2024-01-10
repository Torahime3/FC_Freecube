package fr.torahime.freecube.models;

import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.roles.PlotRoles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {

    //Attributes
    private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();
    private ArrayList<Plot> plots = new ArrayList<>();
    private ArrayList<Request> pendingRequests = new ArrayList<>();
    private UUID uuid;
    private boolean canReceivePlotInfos = true;
    private boolean generalChatActive = true;
    private Player lastPlayerWhoMessaged = null;
    private Inventory lastInventory = null;


    //Constructor
    public GamePlayer(UUID uuid){
        this.uuid = uuid;
    }

    //Methods
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

    public void addRequest(Request request){
        pendingRequests.add(request);
    }

    public void removeRequest(Request request){
        pendingRequests.remove(request);
    }

    public void removePlot(Plot plot){
        plots.remove(plot);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Inventory getLastInventory() {
        return lastInventory;
    }

    public void setLastInventory(Inventory lastInventory) {
        this.lastInventory = lastInventory;
    }

    public boolean canReveicePlotInfos() {
        return canReceivePlotInfos;
    }

    public void setCanReceivePlotInfos(boolean canReceivePlotInfos) {
        this.canReceivePlotInfos = canReceivePlotInfos;
    }

    public ArrayList<Request> getPendingRequests() {
        return pendingRequests;
    }

    public Request getPendingRequest(int id) {
        for(Request request : pendingRequests) {
            if(request.getId() == id) {
                return request;
            }
        }
        return null;
    }

    public boolean isGeneralChatActive() {
        return generalChatActive;
    }

    public void setGeneralChatActive(boolean generalChatActive) {
        this.generalChatActive = generalChatActive;
    }

    public Player getLastPlayerWhoMessaged(){
        if(this.lastPlayerWhoMessaged != null && !this.lastPlayerWhoMessaged.isOnline()){
            this.lastPlayerWhoMessaged = null;
        }
        return this.lastPlayerWhoMessaged;
    }

    public void setLastPlayerWhoMessaged(Player lastPlayerWhoMessaged) {
        this.lastPlayerWhoMessaged = lastPlayerWhoMessaged;
    }
}
