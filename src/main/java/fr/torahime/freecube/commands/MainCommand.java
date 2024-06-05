package fr.torahime.freecube.commands;

import fr.torahime.freecube.commands.players.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainCommand implements CommandExecutor {

    private HashMap<String, CommandExecutor> commandExecutors = new HashMap<>();
    private CommandExecutor helpExecutor = new HelpCommand();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String str, @NotNull String[] args) {

        if(!(sender instanceof Player)) return false;
        if(args.length == 0) return helpExecutor.onCommand(sender, cmd, str, args);

        return commandExecutors.getOrDefault(args[0].toLowerCase(), helpExecutor).onCommand(sender, cmd, str, args);

    }
    public void addCommandExecutor(String commandName, CommandExecutor ce){
        this.commandExecutors.put(commandName, ce);
    }
}
