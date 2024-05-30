package fr.torahime.freecube.listeners;

import fr.torahime.freecube.controllers.GamePlayerLoader;
import fr.torahime.freecube.controllers.customEvents.PlotEnterEvent;
import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.teams.scoreboard.MainBoard;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.Objects;

public class PlayerSessionListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){

        Player player = event.getPlayer();
        GamePlayer gp = GamePlayer.getPlayer(player);
        Location respawnLocation;

        if(Plot.getPlot(gp.getOverPlotId()) != null){
            Plot plot = Plot.getPlot(gp.getOverPlotId());
            respawnLocation = plot.getSpawn();
        } else {
            respawnLocation = PlotIdentifier.getPlotCenterLocation(0);
        }

        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Freecube")), ()
                -> init(player, respawnLocation), 1);

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        event.getDrops().clear();

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        init(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        GamePlayerLoader.savePlayerData(player);
    }

    public void init(Player player){
        Location spawn = PlotIdentifier.getPlotCenterLocation(0);
        init(player, spawn);
    }

    public void init(Player player, Location location){

        //Teleport player to freecube world spawn at plot 0 and clear his inventory
        player.teleport(location);
//        player.setBedSpawnLocation(location, true);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        //Create the iron axe menu item
        giveBaseItems(player);

        //Load player data
        GamePlayerLoader.loadPlayerData(player);

        //Create scoreboard
        MainBoard.createScoreboard(player);

        //Update
        Bukkit.getPluginManager().callEvent(new PlotEnterEvent(player));
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
