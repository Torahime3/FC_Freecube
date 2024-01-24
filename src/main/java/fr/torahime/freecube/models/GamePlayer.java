package fr.torahime.freecube.models;

import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GamePlayer {

    //Attributes
    private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();
    private ArrayList<Plot> plots = new ArrayList<>();
    private ArrayList<Request> pendingRequests = new ArrayList<>();
    private UUID uuid;
    private int overPlotId = -1;
    private boolean generalChatActive = true;
    private Player lastPlayerWhoMessaged = null;
    private Inventory lastInventory = null;


    //Constructor
    public GamePlayer(UUID uuid){
        this.uuid = uuid;
    }

    public void setOverPlotId(int overPlotId) {
        this.overPlotId = overPlotId;
    }

    public int getOverPlotId() {
        return overPlotId;
    }

    public boolean isCanReceivePlotInfos() {
        return overPlotId == -1;
    }

    //Methods
    public static void addGamePlayer(UUID uuid){
        gamePlayers.put(uuid, new GamePlayer(uuid));
    }

    public static GamePlayer getPlayer(UUID playerUUID) {
        return gamePlayers.get(playerUUID);
    }

    public static GamePlayer getPlayer(Player player){
        return getPlayer(player.getUniqueId());
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

    public void playAllSoundsOfPlot(Plot plot){

            for(MusicTransmitter mt : plot.getMusicTransmitters()) {
                playOneSoundOfPlot(mt, plot);
        }
    }

    public void playOneSoundOfPlot(MusicTransmitter mt, Plot plot){

        Player player = Bukkit.getPlayer(uuid);
        if(player != null && PlotIdentifier.isInPlot(player.getLocation()) && plot.getMusicTransmitters().contains(mt)) {

            player.playSound(mt.getLocation(), mt.getMusic().getSound(), mt.getVolume(), mt.getPitch());
            Bukkit.getLogger().info("Playing sound " + mt.getMusic().getName());
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Freecube")), () -> {
                this.playOneSoundOfPlot(mt, plot);
            }, 20L * mt.getMusic().getDuration()); // 20 ticks = 1 second (20 * 60 = 1 minute)
        }
    }

}

