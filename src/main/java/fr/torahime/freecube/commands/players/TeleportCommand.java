package fr.torahime.freecube.commands.players;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage(Component.text("Usage: /tp <player>").color(NamedTextColor.RED));
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if(player == target){
            player.sendMessage(Component.text("Tu ne peux pas te téléporter à toi même").color(NamedTextColor.RED));
            return false;
        }

        if(target == null){
            player.sendMessage(Component.text("Ce joueur n'existe pas").color(NamedTextColor.RED));
            return false;
        }

        if(PlotIdentifier.isInPlot(target.getLocation())){
            player.teleport(PlotIdentifier.getPlotCenterLocation(PlotIdentifier.getPlotIndex(target.getLocation())));
            player.sendMessage(Component.text("[Freecube]").color(NamedTextColor.GOLD)
                    .append(Component.text("Tu as été téléporté dans la même zone que")).color(NamedTextColor.WHITE)
                    .append(Component.text(" " + target.getName()).color(NamedTextColor.AQUA)));
            return true;
        } else {
            player.teleport(target.getLocation());
            return true;
        }

    }
}
