package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        Player player = (Player) sender;

        if(args.length != 2){
            player.sendMessage("Usage: /accept <id>");
            return false;
        }

        //Get request by ID
        int id = Integer.parseInt(args[1]);
        Request request = GamePlayer.getPlayer(player.getUniqueId()).getPendingRequest(id);

        if(request == null){
            player.sendMessage(Component.text("La demande n'existe pas ou n'est plus disponible").color(NamedTextColor.RED));
            return false;
        }

        request.acceptRequestAsync();


        return false;
    }
}
