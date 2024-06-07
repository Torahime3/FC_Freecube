package fr.torahime.freecube.commands.players.plots;

import fr.torahime.freecube.controllers.transaction.ClaimPlotRequest;
import fr.torahime.freecube.controllers.transaction.InviteOnPlotRequest;
import fr.torahime.freecube.controllers.transaction.Request;
import fr.torahime.freecube.models.game.GamePlayer;
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
        GamePlayer gp = GamePlayer.getPlayer(player);

        if(args.length != 2){
            player.sendMessage("Usage: /accept <id>");
            return false;
        }

        //Get request by ID
        int id = Integer.parseInt(args[1]);
        Request request = GamePlayer.getPlayer(player.getUniqueId()).getPendingRequest(id);

        //Check if player has not achieved his max chief plots
        if(request instanceof ClaimPlotRequest && gp.getChefPlotsCount() >= gp.getMAX_CHEF_PLOTS()) {
            player.sendMessage("§cTu as atteint le nombre maximum de zones chef");
            gp.removeRequest(request);
            return true;
        }

        //Check if player has not achieved his max plots
        if(gp.getPlots().size() >= gp.getMAX_PLOTS()) {
            player.sendMessage("§cTu as atteint le nombre maximum de plots");
            gp.removeRequest(request);
            return true;
        }

        if(request == null){
            player.sendMessage(Component.text("La demande n'existe pas ou n'est plus disponible").color(NamedTextColor.RED));
            return false;
        }

        request.acceptRequestAsync();


        return false;
    }
}
