package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.models.game.Plot;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlotDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){

        if (!(event.getEntity() instanceof Player defender)) return;

        Entity damager = event.getDamager();

        if (!PlotIdentifier.isInPlot(defender.getLocation())) {
            event.setCancelled(true);
            return;
        }

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(defender.getLocation()));

        for (PvpArea pa : plot.getPvpAreas()) {
            if (!pa.playerInArea(defender)) {
                continue;
            }

            boolean isMelee = damager instanceof Player;
            boolean isRange = (damager instanceof Arrow && ((Arrow) damager).getShooter() instanceof Player) ||
                    (damager instanceof Trident && ((Trident) damager).getShooter() instanceof Player);

            if (isMelee && !pa.isMeleeWeapon() || isRange && !pa.isRangeWeapon()) {
                event.setCancelled(true);
                return;
            }
        }

    }
}
