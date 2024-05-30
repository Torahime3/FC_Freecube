package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.controllers.transaction.RequestType;
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
            player.sendMessage("§cVous n'êtes pas dans un plot");
            return true;
        }

        //Check if plot is already claimed
        if(PlotIdentifier.isPlotClaimed(playerPosX, playerPosZ)) {
            player.sendMessage("§cCe plot est déjà claim");
            return true;
        }

        //Get plot index
        int plotIndex = PlotIdentifier.getPlotIndex(playerPosX, playerPosZ);
        if(plotIndex == 0) {
            player.sendMessage("§cTu ne peux pas claim le spawn");
            return true;
        }
        //Claim plot
//        Plot plot = Plot.claimPlot(plotIndex, player.getUniqueId());
        Request request = new Request(player, player, plotIndex, RequestType.CLAIM);
        request.sendRequest();

        return false;
    }
}
