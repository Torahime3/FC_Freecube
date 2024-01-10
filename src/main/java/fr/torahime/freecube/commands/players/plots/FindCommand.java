package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FindCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        Player player = (Player) sender;
        return findPlot(player);

    }

    public static boolean findPlot(Player player){
        for(int i = 0; i < 1000000; i++){
            if(!PlotIdentifier.isPlotClaimed(i)){
                GamePlayer.getPlayer(player.getUniqueId()).setCanReceivePlotInfos(true);
                player.teleport(new Location(player.getWorld(), PlotIdentifier.getPlotCenterCoordinates(i)[0], 51, PlotIdentifier.getPlotCenterCoordinates(i)[1]));
                return true;
            }
        }
        return false;
    }
}
