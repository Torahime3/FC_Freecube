package fr.torahime.freecube.listeners;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.teams.scoreboard.MainBoard;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Scoreboard;

public class PlotEnterListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        int playerX = player.getLocation().getBlockX();
        int playerZ = player.getLocation().getBlockZ();

//        Check if a player is in a plot
        if (PlotIdentifier.isInPlot(playerX, playerZ)) {
            MainBoard.updateScoreboard(player);
        }

        //Check if player is in a plot and can claim it
        if (PlotIdentifier.isInPlot(playerX, playerZ) && GamePlayer.getPlayer(player.getUniqueId()).canClaimPlot() && !PlotIdentifier.isPlotClaimed(playerX, playerZ)) {

            player.showTitle(Title.title(Component.text(String.format("Zone LIBRE", PlotIdentifier.getPlotIndex(playerX, playerZ))).color(NamedTextColor.YELLOW),
                    Component.text("Pour l'obtenir fais : ").color(NamedTextColor.WHITE).append(Component.text("/fc claim").color(NamedTextColor.AQUA))));
            GamePlayer.getPlayer(player.getUniqueId()).setCanClaimPlot(false);
            return;
        }

        //Check when player leaves a plot
        if(!PlotIdentifier.isInPlot(playerX, playerZ)) {
            GamePlayer.getPlayer(player.getUniqueId()).setCanClaimPlot(true);
            MainBoard.updateScoreboard(player);
        }

    }

}
