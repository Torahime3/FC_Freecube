package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {
        Player player = (Player) sender;

        if(args.length != 1){
            player.sendMessage(Component.text("Usage: /fc setspawn").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            player.sendMessage(Component.text("Tu dois être dans la zone dans laquelle tu veux changer le spawn").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isPlotClaimed(player.getLocation())){
            player.sendMessage(Component.text("Cette zone n'appartient à personne").color(NamedTextColor.RED));
            return false;
        };

        if(Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.CHIEF && Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.DEPUTY){
            player.sendMessage(Component.text("Tu dois être chef ou adjoint de la zone dans laquelle tu veux changer le spawn").color(NamedTextColor.RED));
            return false;
        }

        int X = player.getLocation().getBlockX();
        int Z = player.getLocation().getBlockZ();
        int Y = player.getWorld().getHighestBlockYAt(X, Z) + 1;

        Location spawn = new Location(player.getWorld(), X, Y, Z);

        Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).setSpawn(spawn);
        player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Spawn de la zone changé").color(NamedTextColor.GREEN)));

        return false;
    }
}
