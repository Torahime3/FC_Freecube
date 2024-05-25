package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {


    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event){

            event.setCancelled(true);
            event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), 0, false, false);

        }
}
