package fr.torahime.freecube.listeners;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        //Create the iron axe menu item
        ItemBuilder iron_axe = new ItemBuilder(Material.IRON_AXE, 1);
        iron_axe.setDisplayName(Component.text("Ouvrir le menu").decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" (clic droit)").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)));

        iron_axe.setUnbreakable(true);
        iron_axe.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        iron_axe.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        //Teleport player to freecube world spawn and give him the iron axe
        player.teleport(new Location(player.getServer().getWorld("freecube"),0,51,0));
        player.getInventory().clear();
        player.getInventory().setItem(8, iron_axe.getItem());


        GamePlayer.addGamePlayer(player.getUniqueId());

    }

}
