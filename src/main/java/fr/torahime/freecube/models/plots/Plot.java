package fr.torahime.freecube.models.plots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.controllers.custom_events.PlotUpdateEvent;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.interactions.InteractionsMap;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.preferences.PreferencesMap;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.weather.Weather;
import fr.torahime.freecube.services.plots.PlotService;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Plot {

    private static HashMap<Integer, Plot> plots = new HashMap<>();

    @Expose
    @SerializedName("plotId")
    private int id;
    @Expose
    @SerializedName("plotName")
    private String name;
    @Expose
    private Location spawn;
    @Expose
    private Hours hour = Hours.TWELVE;
    @Expose
    private Weather weather = Weather.CLEAR;
    @Expose
    private ArrayList<MusicTransmitter> musicTransmitters = new ArrayList<>();
    @Expose
    private ArrayList<EntityGenerator> entityGenerators = new ArrayList<>();
    @Expose
    private ArrayList<PvpArea> pvpAreas = new ArrayList<>();
    @Expose
    private PreferencesMap preferences = new PreferencesMap();
    @Expose
    private InteractionsMap interactions = new InteractionsMap();
    @Expose
    private final HashMap<UUID, PlotRoles> members = new HashMap<>();

    private final int MAX_MEMBERS = 16;
    private final int MAX_MUSIC_TRANSMITTERS = 8;
    private final int MAX_ENTITY_GENERATORS = 4;
    private final int MAX_PVP_AREAS = 4;

    public Plot(int id, UUID owner) {
        this.name = "";
        this.id = id;
        this.spawn = PlotIdentifier.getPlotCenterLocation(id);
        this.addPlayer(owner, PlotRoles.CHIEF);
    }

    public int getId() {
        return id;
    }

    public static Plot claimPlot(int id, UUID owner){
        return claimPlot(id, owner, null);
    }

    public static Plot claimPlot(int id, UUID owner, Plot plot) {

        PlotService plotService = new PlotService();

        // Create a new plot if it doesn't exist in the database
        if(plot == null){
            try {
                plot = new Plot(id, owner);
                if (plotService.create(plot)) {
                    plots.put(id, plot);
                    plot.updateAllPlayersOverPlot();
                    return plot;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        // Update the plot if it already exists in the database
        } else {
            plots.put(id, plot);
            if(GamePlayer.getPlayer(owner) == null ) {
                GamePlayer.addGamePlayer(owner);
            }
            GamePlayer.getPlayer(owner).addPlot(plot);
            plot.updateAllPlayersOverPlot();
            return plot;
        }

        return null;
    }

    public void addPlayer(UUID playerUUID, PlotRoles plotRole) {
        if (members.size() >= MAX_MEMBERS || isPlayerPresent(playerUUID)) {
            return;
        }
        members.put(playerUUID, plotRole);
        GamePlayer.getPlayer(playerUUID).addPlot(this);
        this.updateAllPlayersOverPlot();
        this.save();
    }

    public void addPvpArea(PvpArea pvpArea) {
        this.pvpAreas.add(pvpArea);
    }

    public int getMAX_MUSIC_TRANSMITTERS() {
        return MAX_MUSIC_TRANSMITTERS;
    }

    public int getMAX_ENTITY_GENERATORS() {
        return MAX_ENTITY_GENERATORS;
    }

    public int getMAX_PVP_AREAS() {
        return MAX_PVP_AREAS;
    }

    public void removePvpArea(PvpArea pvpArea) {
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
        if (musicTransmitters.size() < 8) {
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

    public PlotRoles getMemberRole(Player player){
        return getMemberRole(player.getUniqueId());
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

    public void updateAllPlayersOverPlot() {
        Bukkit.getScheduler().runTask(Freecube.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {

                if (GamePlayer.getPlayer(p).getOverPlotId() == this.id) {
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
        PlotService plotService = new PlotService();
        return plotService.update(this);
    }
}

