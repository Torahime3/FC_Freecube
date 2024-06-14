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

        if(args.length < 3){
            sender.sendMessage("§cUsage: /fc setrank <player> <rank>");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        Ranks rank;

        try{
            rank = Ranks.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e){
            sender.sendMessage("§cRank not found");
            return false;
        }

        if(target == null){
            sender.sendMessage("Player not found");
            return false;
        }

        System.out.println(target.getName());
        System.out.println(rank.getPrefix());

        GamePlayer gp = GamePlayer.getPlayer(target.getUniqueId());
        gp.setRank(rank);

        return true;

    }
}
