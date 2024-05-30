package fr.torahime.freecube.listeners.players;

import fr.torahime.freecube.controllers.menus.MainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PlayerOpenFCMainMenuListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if(item == null) return;

        //Check if player right-clicked with the right iron axe
        if(item.getType() == Material.IRON_AXE && item.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)){
            if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
                new MainMenu(player).openMenu();
            }
        }

    }
}
