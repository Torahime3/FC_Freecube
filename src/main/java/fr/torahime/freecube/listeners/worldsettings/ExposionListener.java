package fr.torahime.freecube.listeners.worldsettings;

import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.HashMap;

public class ExposionListener implements Listener {


    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event){

            event.setCancelled(true);
            event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), 0, false, false);

        }
}
