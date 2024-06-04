package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.controllers.custom_events.PlotEnterEvent;
import fr.torahime.freecube.controllers.custom_events.PlotQuitEvent;
import fr.torahime.freecube.controllers.custom_events.PlotUpdateEvent;
import fr.torahime.freecube.models.game.GamePlayer;
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

public class PlotBoundaryListener implements Listener {

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

//        Plot plot = Plot.getPlot(GamePlayer.getPlayer(player).getOverPlotId());
//        if(plot != null) {
//            plot.save();
//        }
//        if(plot == null){
//            PlotService plotService = new PlotService();
//            plot = plotService.get(String.valueOf(GamePlayer.getPlayer(player).getOverPlotId()));
//            if(plot != null){
//                Plot.claimPlot(plot.getId(), plot.getOwner(), plot);
//                return;
//            }
//        }

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

//        Plot Quit
        if (!PlotIdentifier.isInPlot(player.getLocation()) && !GamePlayer.getPlayer(player.getUniqueId()).isCanReceivePlotInfos()) {
            Bukkit.getPluginManager().callEvent(new PlotQuitEvent(event.getPlayer()));
            GamePlayer.getPlayer(player.getUniqueId()).setOverPlotId(-1);
        }

//        Plot Enter
        if (PlotIdentifier.isInPlot(player.getLocation()) && GamePlayer.getPlayer(player.getUniqueId()).isCanReceivePlotInfos()) {
            Bukkit.getPluginManager().callEvent(new PlotEnterEvent(event.getPlayer()));
            GamePlayer.getPlayer(player.getUniqueId()).setOverPlotId(PlotIdentifier.getPlotIndex(player.getLocation()));
        }

    }

}
