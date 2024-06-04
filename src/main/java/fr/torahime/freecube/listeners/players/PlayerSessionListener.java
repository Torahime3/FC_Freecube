package fr.torahime.freecube.listeners.players;

import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.controllers.loaders.GamePlayerLoader;
import fr.torahime.freecube.controllers.custom_events.PlotEnterEvent;
import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
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

public class PlayerSessionListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){

        Player player = event.getPlayer();
        GamePlayer gp = GamePlayer.getPlayer(player);
        Location respawnLocation;

        //If the player is in a plot, respawn him in the plot spawn
        if(Plot.getPlot(gp.getOverPlotId()) != null){
            Plot plot = Plot.getPlot(gp.getOverPlotId());
            respawnLocation = plot.getSpawn();
        } else { //Else respawn him at the freecube spawn
            respawnLocation = PlotIdentifier.getPlotCenterLocation(0);
        }

        // Delay the teleportation to avoid the player to be teleported but
        Bukkit.getScheduler().runTaskLater(Freecube.getInstance(), ()
                -> init(player, respawnLocation, true), 1);

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
        GamePlayer.getPlayer(player).save();
    }

    public void init(Player player){
        Location spawn = PlotIdentifier.getPlotCenterLocation(0);
        init(player, spawn, false);
    }

    public void init(Player player, Location location, boolean isRespawning){

        //Teleport player to freecube world spawn at plot 0 and clear his inventory
        player.teleport(location);
//        player.setBedSpawnLocation(location, true);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        //Create the iron axe menu item
        giveBaseItems(player);

        //Load player data
        if(!isRespawning){
            GamePlayerLoader.loadPlayerData(player);
        }

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
