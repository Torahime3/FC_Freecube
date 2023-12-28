package fr.torahime.freecube.listeners;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.teams.scoreboard.MainBoard;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        //Teleport player to freecube world spawn and clear his inventory
        player.teleport(new Location(player.getServer().getWorld("freecube"),0,51,0));
        player.getInventory().clear();

        //Create the iron axe menu item
        giveBaseItems(player);

        //Create gameplayer
        GamePlayer.addGamePlayer(player.getUniqueId());

        //Create scoreboard
        MainBoard.createScoreboard(player);

    }


    public static void giveBaseItems(Player player){
        ItemBuilder iron_axe = new ItemBuilder(Material.IRON_AXE, 1);
        iron_axe.setDisplayName(Component.text("Ouvrir le menu").decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" (clic droit)").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)));

        iron_axe.setUnbreakable(true);
        iron_axe.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        iron_axe.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        player.getInventory().setItem(8, iron_axe.getItem());
    }

}
