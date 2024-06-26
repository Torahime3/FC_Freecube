package fr.torahime.freecube.controllers.custom_events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlotUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public PlotUpdateEvent(Player player){
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}
