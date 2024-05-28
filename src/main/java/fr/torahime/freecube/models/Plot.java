package fr.torahime.freecube.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.controllers.customEvents.PlotUpdateEvent;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.interactions.InteractionsMap;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.preferences.PreferencesMap;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.models.weather.Weather;
import fr.torahime.freecube.services.plots.PlotService;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Plot {

    private static HashMap<Integer, Plot> plots = new HashMap<>();

    @Expose @SerializedName("plotId") private int id;
    @Expose @SerializedName("plotName") private String name;
    @Expose private Location spawn;
    @Expose private Hours hour = Hours.TWELVE;
    @Expose private Weather weather = Weather.CLEAR;
    @Expose private ArrayList<MusicTransmitter> musicTransmitters = new ArrayList<>();
    @Expose private ArrayList<EntityGenerator> entityGenerators = new ArrayList<>();
    @Expose private ArrayList<PvpArea> pvpAreas = new ArrayList<>();
    @Expose private PreferencesMap preferences = new PreferencesMap();
    @Expose private InteractionsMap interactions = new InteractionsMap();
    @Expose private final HashMap<UUID, PlotRoles> members = new HashMap<>();
    private final int MAX_MEMBERS = 16;

    public Plot(int id, UUID owner) {
        this.name = "";
        this.id = id;
        this.spawn = PlotIdentifier.getPlotCenterLocation(id);
        this.addPlayer(owner, PlotRoles.CHIEF);
    }

    public int getId() {
        return id;
    }

    public static Plot claimPlot(int id, UUID owner) {

        //Create the plot
        Plot plot = new Plot(id, owner);

        PlotService plotService = new PlotService();
        try {
            if(plotService.createPlot(plot)){
                plots.put(id, plot);
                plot.updateAllPlayersOverPlot();
                return plot;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addPlayer(UUID playerUUID, PlotRoles plotRole) {
        if (members.size() >= MAX_MEMBERS || isPlayerPresent(playerUUID)) {
            return false;
        }
        members.put(playerUUID, plotRole);
        GamePlayer.getPlayer(playerUUID).addPlot(this);
        this.updateAllPlayersOverPlot();
        return true;
    }

    public void addPvpArea(PvpArea pvpArea){
        this.pvpAreas.add(pvpArea);
    }

    public void removePvpArea(PvpArea pvpArea){
        this.pvpAreas.remove(pvpArea);
    }

    public ArrayList<PvpArea> getPvpAreas() {
        return pvpAreas;
    }

    public MusicTransmitter getMusicTransmitterByLocation(Location location) {
        for (MusicTransmitter musicTransmitter : musicTransmitters) {
            if (musicTransmitter.getLocation().equals(location)) {
                return musicTransmitter;
            }
        }
        return null;
    }

    public ArrayList<EntityGenerator> getEntityGenerators() {
        return entityGenerators;
    }

    public void addEntityGenerator(EntityGenerator entityGenerator) {
        entityGenerators.add(entityGenerator);
    }

    public void removeEntityGenerator(EntityGenerator entityGenerator) {
        entityGenerators.remove(entityGenerator);
    }

    public ArrayList<MusicTransmitter> getMusicTransmitters() {
        return musicTransmitters;
    }

    public void addMusicTransmitter(MusicTransmitter musicTransmitter) {
        if(musicTransmitters.size() < 8){
            musicTransmitters.add(musicTransmitter);
        }

    }
    public void removeMusicTransmitter(MusicTransmitter musicTransmitter) {
        musicTransmitters.remove(musicTransmitter);
    }

    public void setHour(Hours hour) {
        this.hour = hour;
    }

    public Hours getHour() {
        return hour;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }

    public boolean isPlayerPresent(UUID playerUUID) {
        return members.containsKey(playerUUID);
    }

    public void removePlayer(UUID playerId) {
        members.remove(playerId);
        GamePlayer.getPlayer(playerId).removePlot(this);
        this.updateAllPlayersOverPlot();
    }

    public static Plot getPlot(int id) {
        return plots.get(id);
    }

    public PlotRoles getMemberRole(UUID member) {
        if (!isPlayerPresent(member)) {
            return PlotRoles.GUEST;
        }
        return members.get(member);
    }

    public UUID getOwner() {
        for (UUID player : members.keySet()) {
            if (members.get(player) == PlotRoles.CHIEF) {
                return player;
            }
        }
        return null;
    }

    public void updateAllPlayersOverPlot(){
        Bukkit.getScheduler().runTask(Freecube.getInstance(), () -> {
            for(Player p : Bukkit.getOnlinePlayers()){

                if(GamePlayer.getPlayer(p).getOverPlotId() == this.id){
                    Bukkit.getPluginManager().callEvent(new PlotUpdateEvent(p));

                }

            }
        });
    }

    public Set<UUID> getMembers() {
        return members.keySet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setMemberRole(UUID member, PlotRoles role) {
        members.replace(member, role);
        this.updateAllPlayersOverPlot();
    }

    public Location getSpawn() {
        return spawn;
    }

    public PreferencesMap getPreferences() {
        return preferences;
    }

    public InteractionsMap getInteractions() {
        return interactions;
    }

    public boolean save(){
        System.out.println("Sauvegarde en cours");
        PlotService plotService = new PlotService();
        plotService.updatePlot(this);
        return true;
    }
}

