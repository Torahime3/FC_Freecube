package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetNameCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {
        Player player = (Player) sender;

        if(args.length < 2){
            player.sendMessage(Component.text("Usage: /fc setname <name>").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            player.sendMessage(Component.text("Tu dois être dans la zone dans laquelle tu veux changer le nom").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isPlotClaimed(player.getLocation())){
            player.sendMessage(Component.text("Cette zone n'appartient à personne").color(NamedTextColor.RED));
            return false;
        };

        if(Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.CHIEF && Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.DEPUTY){
            player.sendMessage(Component.text("Tu dois être chef ou adjoint de la zone dans laquelle tu veux changer le nom").color(NamedTextColor.RED));
            return false;
        }

        StringBuilder name = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            name.append(args[i]).append(" ");
        }

        if(name.length() > 20){
            player.sendMessage(Component.text("Le nom de la zone ne peut pas dépasser 20 caractères").color(NamedTextColor.RED));
            return false;
        }

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
        plot.setName(name.toString());
        plot.updateAllPlayersOverPlot();
        plot.save();
        player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Nom de la zone changé").color(NamedTextColor.GREEN)));

        return true;
    }
}
