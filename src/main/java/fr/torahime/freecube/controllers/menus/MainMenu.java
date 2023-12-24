package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class MainMenu implements Listener {

    public void openMenu(Player player){
        //Create inventory
        Inventory inv = Bukkit.createInventory(null, 54);

        //Create buttons (items)
        ItemBuilder myPlots = new ItemBuilder(Material.OAK_SIGN);
        myPlots.setDisplayName("§6Mes zones");

        inv.setItem(0, myPlots.getItem());

        player.openInventory(inv);
    }

}
