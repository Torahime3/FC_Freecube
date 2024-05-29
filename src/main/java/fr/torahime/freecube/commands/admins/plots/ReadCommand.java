package fr.torahime.freecube.commands.admins.plots;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.services.plots.PlotService;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!player.isOp()) return false;

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            player.sendMessage(Component.text("You must be in a plot to read it").color(NamedTextColor.RED));
            return false;
        }

        if(PlotIdentifier.isPlotClaimed(player.getLocation())) return false;

        PlotService plotService = new PlotService();
        Plot plot = plotService.get(PlotIdentifier.getPlotIndex(player.getLocation()));

        if (plot == null) {
            player.sendMessage(Component.text("Failed to read plot").color(NamedTextColor.RED));
            return false;
        }

        player.sendMessage(Component.text("Plot read successfully").color(NamedTextColor.GREEN));
        Plot.claimPlot(plot.getId(), plot.getOwner(), plot);

        return false;
    }
}
