package fr.torahime.freecube.commands.players;

import fr.torahime.freecube.listeners.players.PlayerChatListener;
import fr.torahime.freecube.models.game.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PrivateMessageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        Player player = (Player) sender;
        GamePlayer gp = GamePlayer.getPlayer(player);

        if(cmd.getName().equals("msg") || cmd.getName().equals("m")){

            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null){
                player.sendMessage("§cCe joueur n'existe pas ou n'est pas connecté");
                return false;
            }

            if(player == target){
                player.sendMessage("§cTu ne peux pas t'envoyer de message à toi-même");
                return false;
            }

            String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            PlayerChatListener.sendMessageFcFormat(gp, player, target, -1, Component.text(message, NamedTextColor.WHITE), true);

            return true;
        }

        if(cmd.getName().equals("r")){

            Player target = GamePlayer.getPlayer(player.getUniqueId()).getLastPlayerWhoMessaged();
            if(target == null){
                player.sendMessage("§cLe destinaire n'existe pas");
                return false;
            }

            String message = String.join(" ", args);
            PlayerChatListener.sendMessageFcFormat(gp, player, target, -1, Component.text(message, NamedTextColor.WHITE), true);

        }

        return false;
    }
}
