package fr.torahime.freecube.models.game;

import com.google.gson.annotations.Expose;
import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
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
    private boolean isOpeningFreecubeMenu;
    private boolean isCurrentMenuFreecube;
    private List<Integer> musicTaskIds;


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
        this.isOpeningFreecubeMenu = false;
        this.isCurrentMenuFreecube = false;
        this.musicTaskIds = new ArrayList<>();
    }
    //Constructor
    public GamePlayer(UUID uuid){
        this.uuid = uuid;
        this.plotsIds = new ArrayList<>();
        this.plots = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
        this.overPlotId = -1;
        this.generalChatActive = true;
        this.lastPlayerWhoMessaged = null;
        this.isOpeningFreecubeMenu = false;
        this.isCurrentMenuFreecube = false;
        this.musicTaskIds = new ArrayList<>();
    }

    //Methods
    public static void addGamePlayer(UUID uuid){
        addGamePlayer(uuid, new GamePlayer(uuid));
    }

    public static void addGamePlayer(UUID uuid, GamePlayer gamePlayer){
        gamePlayers.put(uuid, gamePlayer);
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

    public boolean isOpeningFreecubeMenu() {
        return isOpeningFreecubeMenu;
    }

    public void setOpeningFreecubeMenu(boolean openingFreecubeMenu) {
        isOpeningFreecubeMenu = openingFreecubeMenu;
    }

    public boolean isCurrentMenuFreecube() {
        return isCurrentMenuFreecube;
    }

    public void setCurrentMenuFreecube(boolean currentMenuFreecube) {
        isCurrentMenuFreecube = currentMenuFreecube;
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
        if (player != null && PlotIdentifier.isInPlot(player.getLocation()) && plot.getMusicTransmitters().contains(mt)) {
            player.playSound(mt.getLocation(), mt.getMusic().getSound(), mt.getVolume(), mt.getPitch());
            int taskId = Bukkit.getScheduler().runTaskLater(Freecube.getInstance(), () -> {
                this.playOneSoundOfPlot(mt, plot);
            }, 20L * mt.getMusic().getDuration()).getTaskId();

            musicTaskIds.add(taskId);
        }
    }

    public void stopAllSounds() {
        for (Integer taskId : musicTaskIds) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        musicTaskIds.clear();
    }

    public boolean save(){
        GamePlayerService gamePlayerService = new GamePlayerService();
        return gamePlayerService.update(this);
    }

}

