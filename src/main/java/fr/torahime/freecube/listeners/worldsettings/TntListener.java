package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TntListener implements Listener {

    @EventHandler
    public void onTntExplode(EntityExplodeEvent event){

        if(event.getEntityType() == EntityType.PRIMED_TNT || event.getEntityType() == EntityType.MINECART_TNT){
            event.blockList().clear();
            event.getLocation().getWorld().createExplosion(event.getLocation(), 3, false, false);
        }

    }

}
