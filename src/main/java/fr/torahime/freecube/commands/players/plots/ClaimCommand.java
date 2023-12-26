package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        Player player = (Player) sender;
        int playerPosX = player.getLocation().getBlockX();
        int playerPosZ = player.getLocation().getBlockZ();

        //Check if player is in a plot
        if(!PlotIdentifier.isInPlot(playerPosX, playerPosZ)) {
            player.sendMessage("§cYou are not in a plot");
            return true;
        }

        //Check if plot is already claimed
        if(PlotIdentifier.isPlotClaimed(playerPosX, playerPosZ)) {
            player.sendMessage("§cThis plot is already claimed");
            return true;
        }

        //Get plot index
        int plotIndex = PlotIdentifier.getPlotIndex(playerPosX, playerPosZ);
        //Claim plot
        Plot.claimPlot(plotIndex, player.getUniqueId());
        player.sendMessage(String.format("§aPlot %s claimed", plotIndex));

        return false;
    }
}
