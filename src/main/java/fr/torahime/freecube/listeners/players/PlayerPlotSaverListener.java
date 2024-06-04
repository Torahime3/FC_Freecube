package fr.torahime.freecube.listeners.players;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerPlotSaverListener implements Listener {

    @EventHandler
    public void onPlayerOpenInventory(InventoryOpenEvent event){
        Player player = (Player) event.getPlayer();
        GamePlayer gp = GamePlayer.getPlayer(player);
        gp.setCurrentMenuFreecube(gp.isOpeningFreecubeMenu());
        gp.setOpeningFreecubeMenu(false);
    }

    @EventHandler
    public void onPlayerCloseFreecubeMenu(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!PlotIdentifier.isInPlot(player.getLocation())) return;

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

        if(plot == null) return;

        if(GamePlayer.getPlayer(player).isCurrentMenuFreecube() && !GamePlayer.getPlayer(player).isOpeningFreecubeMenu()){
            if(plot.getMemberRole(player) == PlotRoles.CHIEF || plot.getMemberRole(player) == PlotRoles.DEPUTY){
                plot.save();
            }
        }

        GamePlayer.getPlayer(player).setCurrentMenuFreecube(false);
    }
}
