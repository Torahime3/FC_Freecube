package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.pvp.PvpWeapons;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class PlotPvpListener implements Listener {

    private boolean isValidLocation(Location location) {
        return PlotIdentifier.isInPlot(location) && PlotIdentifier.isPlotClaimed(location);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (!isValidLocation(event.getEntity().getLocation())) {
            event.setCancelled(true);
            return;
        }

        Projectile projectile = event.getEntity();
        if (projectile.getShooter() instanceof Player player) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            projectile.setMetadata("weapon", new FixedMetadataValue(Freecube.getInstance(), itemInHand.getType().toString()));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(isValidLocation(event.getDamager().getLocation()) && isValidLocation(event.getEntity().getLocation()))) {
            event.setCancelled(true);
            return;
        }

        Entity damagerEntity = event.getDamager();
        Entity victimEntity = event.getEntity();

        if (!(damagerEntity instanceof Player) && !(damagerEntity instanceof AbstractArrow)) {
            event.setCancelled(true);
            return;
        }

        Player damager = null;
        String itemDamager = null;
        if (damagerEntity instanceof Player) {
            damager = (Player) damagerEntity;
            itemDamager = damager.getInventory().getItemInMainHand().getType().toString();
        } else {
            AbstractArrow arrow = (AbstractArrow) damagerEntity;
            if (arrow.getShooter() instanceof Player) {
                damager = (Player) arrow.getShooter();
            }
            if (arrow.hasMetadata("weapon")) {
                List<MetadataValue> metadata = arrow.getMetadata("weapon");
                itemDamager = metadata.get(0).asString();
            }
        }

        if (!(victimEntity instanceof Player victim)) {
            event.setCancelled(true);
            return;
        }

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(damager.getLocation()));
        if (plot != null) {
            for (PvpArea pvpArea : plot.getPvpAreas()) {
                if (pvpArea.playerInArea(damager) && pvpArea.playerInArea(victim)) {
                    if (pvpArea.getPvpWeaponsMap().get(PvpWeapons.getByName(itemDamager)) == PlotStates.DEACTIVATE) {
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(false);
                        return;
                    }
                }
            }
        }
        event.setCancelled(true);
    }
}
