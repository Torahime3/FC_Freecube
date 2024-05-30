package fr.torahime.freecube.models;

import com.google.gson.annotations.Expose;
import fr.torahime.freecube.controllers.GamePlayerLoader;
import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.services.gameplayers.GamePlayerService;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GamePlayer {

    //Attributes
    private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

    @Expose
    private UUID uuid;
    @Expose
    private ArrayList<Integer> plotsIds;
    private ArrayList<Plot> plots;
    private int overPlotId;
    private boolean generalChatActive;
    private Player lastPlayerWhoMessaged;
    private ArrayList<Request> pendingRequests;

//    Method for when the gameplayer is loaded from the database
    public void initializeGamePlayer(){
        if(plots == null ){
            plots = new ArrayList<>();
        }
        if(pendingRequests == null){
            pendingRequests = new ArrayList<>();
        }
        this.overPlotId = -1;
        this.generalChatActive = true;
        this.lastPlayerWhoMessaged = null;
    }
    //Constructor
    public GamePlayer(UUID uuid){
        this.uuid = uuid;
        this.overPlotId = -1;
        this.generalChatActive = true;
        this.lastPlayerWhoMessaged = null;
        this.plots = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
        this.plotsIds = new ArrayList<>();

    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
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
        addGamePlayer(uuid, new GamePlayer(uuid));
    }

    public static void addGamePlayer(UUID uuid, GamePlayer gamePlayer){
        gamePlayers.put(uuid, gamePlayer);
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

    public ArrayList<Integer> getPlotsIds() {
        return plotsIds;
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
        if(!plotsIds.contains(plot.getId())){
            System.out.println("Ajout d'un nouvel id de plot - " + plot.getId());
            plotsIds.add(plot.getId());
            this.save();
        }

    }

    public void addRequest(Request request){
        pendingRequests.add(request);
    }

    public void removeRequest(Request request){
        pendingRequests.remove(request);
    }

    public void removePlot(Plot plot){
        plots.remove(plot);
        plotsIds.remove((Integer) plot.getId());
        this.save();
    }

    public UUID getUuid() {
        return uuid;
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

    public boolean save(){
        GamePlayerService gamePlayerService = new GamePlayerService();
        return gamePlayerService.update(this);
    }

}

