package fr.torahime.freecube.listeners.worldsettings;

import fr.torahime.freecube.controllers.customEvents.PlotEnterEvent;
import fr.torahime.freecube.controllers.customEvents.PlotQuitEvent;
import fr.torahime.freecube.controllers.customEvents.PlotUpdateEvent;
import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.teams.scoreboard.MainBoard;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlotEnterListener implements Listener {

    @EventHandler
    public void onPlayerUpdateOnPlot(PlotUpdateEvent event){

        MainBoard.updateScoreboard(event.getPlayer());

    }

    @EventHandler
    public void onPlayerEnterPlot(PlotEnterEvent event){

        Player player = event.getPlayer();
        int playerX = player.getLocation().getBlockX();
        int playerZ = player.getLocation().getBlockZ();

        GamePlayer.getPlayer(player).setOverPlotId(PlotIdentifier.getPlotIndex(player.getLocation()));
        Bukkit.getPluginManager().callEvent(new PlotUpdateEvent(event.getPlayer()));

        if (!PlotIdentifier.isPlotClaimed(playerX, playerZ) && PlotIdentifier.getPlotIndex(playerX, playerZ) != 0) {
            player.showTitle(Title.title(Component.text(String.format("Zone LIBRE", PlotIdentifier.getPlotIndex(playerX, playerZ))).color(NamedTextColor.YELLOW),
                    Component.text("Pour l'obtenir fais : ").color(NamedTextColor.WHITE).append(Component.text("/fc claim").color(NamedTextColor.AQUA))));
        }
    }

    @EventHandler
    public void onPlayerQuitPlot(PlotQuitEvent event){
        GamePlayer.getPlayer(event.getPlayer()).setOverPlotId(-1);
        Bukkit.getPluginManager().callEvent(new PlotUpdateEvent(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if(!PlotIdentifier.isInPlot(player.getLocation()) && !GamePlayer.getPlayer(player.getUniqueId()).isCanReceivePlotInfos()){
            Bukkit.getPluginManager().callEvent(new PlotQuitEvent(event.getPlayer()));
            GamePlayer.getPlayer(player.getUniqueId()).setOverPlotId(-1);
        }

        if(PlotIdentifier.isInPlot(player.getLocation()) && GamePlayer.getPlayer(player.getUniqueId()).isCanReceivePlotInfos()){
            Bukkit.getPluginManager().callEvent(new PlotEnterEvent(event.getPlayer()));
            GamePlayer.getPlayer(player.getUniqueId()).setOverPlotId(PlotIdentifier.getPlotIndex(player.getLocation()));
        }

    }

}
