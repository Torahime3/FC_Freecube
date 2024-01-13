package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class WaterLavaSpreadListener implements Listener {

    @EventHandler
    public void onWaterLavaSpread(BlockFromToEvent event){
        event.setCancelled(true);
    }

}
