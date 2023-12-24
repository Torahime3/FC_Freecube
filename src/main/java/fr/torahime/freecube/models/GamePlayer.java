package fr.torahime.freecube.models;

import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {

    private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

    public static void addGamePlayer(UUID uuid){
        gamePlayers.put(uuid, new GamePlayer(uuid));
    }

    public static GamePlayer getPlayer(UUID playerUUID) {
        return gamePlayers.get(playerUUID);
    }

    private UUID uuid;
    private boolean canClaimPlot = true;

    public GamePlayer(UUID uuid){
        this.uuid = uuid;
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
