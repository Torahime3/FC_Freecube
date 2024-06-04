package fr.torahime.freecube.commands.admins.plots;

import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!player.isOp()) return false;

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            player.sendMessage(Component.text("You must be in a plot to save it").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isPlotClaimed(player.getLocation())) return false;

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

        plot.save();
        player.sendMessage(Component.text("Plot saved successfully").color(NamedTextColor.GREEN));

        return true;
    }
}
