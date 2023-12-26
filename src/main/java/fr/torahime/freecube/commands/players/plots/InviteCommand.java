package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InviteCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(args.length != 2){
            player.sendMessage(Component.text("Usage: /fc invite <player>").color(NamedTextColor.RED));
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[1]);

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            player.sendMessage(Component.text("Tu dois être dans la zone dans laquelle tu veux inviter un joueur").color(NamedTextColor.RED));
            return false;
        }

        if(!PlotIdentifier.isPlotClaimed(player.getLocation())) return false;

        if(Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.CHIEF && Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId()) != PlotRoles.DEPUTY){
            player.sendMessage(Component.text("Tu dois être chef ou adjoint de la zone dans laquelle tu veux inviter un joueur").color(NamedTextColor.RED));
            return false;
        }

        if(player == target){
            player.sendMessage(Component.text("Tu ne peux pas t'inviter toi même").color(NamedTextColor.RED));
            return false;
        }

        if(target == null){
            player.sendMessage(Component.text("Ce joueur n'existe pas").color(NamedTextColor.RED));
            return false;
        }

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

        if(plot.getMembers().contains(target.getUniqueId())){
            player.sendMessage(Component.text("Ce joueur est déjà membre de la zone").color(NamedTextColor.RED));
            return false;
        }

        Request request = new Request(player.getUniqueId(), target.getUniqueId(), plot.getId());
        request.sendRequest();
        return true;
    }
}
