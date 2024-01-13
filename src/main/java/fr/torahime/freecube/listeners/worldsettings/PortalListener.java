package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onPlayerGoThroughPortal(PlayerPortalEvent event){

        event.setCancelled(true);

    }
}
