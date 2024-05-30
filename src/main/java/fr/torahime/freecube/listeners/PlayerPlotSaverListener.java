package fr.torahime.freecube.listeners;

import fr.torahime.freecube.controllers.menus.MainMenu;
import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

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

        System.out.println(player.getOpenInventory().getType());

        if(player.getOpenInventory().getType() == InventoryType.CREATIVE || player.getOpenInventory().getType() == InventoryType.CRAFTING) return;

        if(!PlotIdentifier.isInPlot(player.getLocation())) return;

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

        if(plot == null) return;

        if(GamePlayer.getPlayer(player).isCurrentMenuFreecube() && !GamePlayer.getPlayer(player).isOpeningFreecubeMenu()){
            plot.save();
        }

    }
}
