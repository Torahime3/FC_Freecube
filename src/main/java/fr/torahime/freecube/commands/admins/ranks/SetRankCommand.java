package fr.torahime.freecube.commands.admins.ranks;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        if(args.length < 2){
            sender.sendMessage("Usage: /fc setrank <player> <rank>");
            return false;
        }

//        Player target = Bukkit.getPlayerExact(args[1]);
//        Ranks rank = Ranks.valueOf(args[2].toUpperCase());

        System.out.println(args[1]);
        System.out.println(args[2]);
//        if(target == null){
//            sender.sendMessage("Player not found");
//            return false;
//        }
//
//        if(rank == null){
//            sender.sendMessage("Rank not found");
//            return false;
//        }
//
//        GamePlayer gp = GamePlayer.getPlayer(target.getUniqueId());
//        gp.setRank(rank);

        return true;

    }
}
