package fr.torahime.freecube.teams.scoreboard;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import javax.inject.Named;

public class MainBoard {

    public static void createScoreboard(Player player){
        Component scoreboardName = Component.text("Fun").decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD)
                .append(Component.text("Cube").decorate(TextDecoration.BOLD).color(NamedTextColor.WHITE))
                .append(Component.text(".net").decoration(TextDecoration.BOLD, false).color(NamedTextColor.WHITE));

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("plotboard", Criteria.DUMMY, scoreboardName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§l").setScore(3);
        objective.getScore("§a").setScore(0);


//        Create role plot team
        Team roleTeam = scoreboard.registerNewTeam("role");
        String roleTeamKey = "§r";
        roleTeam.addEntry(roleTeamKey);
        objective.getScore(roleTeamKey).setScore(1);

        //Create plot team
        Team plotIdTeam = scoreboard.registerNewTeam("plotid");
        String plotTeamKey = "§k";
        plotIdTeam.addEntry(plotTeamKey);
        objective.getScore(plotTeamKey).setScore(2);

        player.setScoreboard(scoreboard);
    }

    public static void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        Team plotIdTeam = scoreboard.getTeam("plotid");
        Team roleTeam = scoreboard.getTeam("role");

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            plotIdTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text("Route").color(NamedTextColor.GRAY));
            roleTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            return;
        }

        if (PlotIdentifier.isInPlot(player.getLocation()) && !PlotIdentifier.isPlotClaimed(player.getLocation())) {
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Zone LIBRE").color(NamedTextColor.YELLOW));
            roleTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            return;
        }

        if(PlotIdentifier.isInPlot(player.getLocation()) && PlotIdentifier.isPlotClaimed(player.getLocation()) && !PlotIdentifier.isMemberOfPlot(player.getLocation().getBlockX(), player.getLocation().getBlockZ(), player.getUniqueId())){
            PlotRoles role = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId());
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Rang: ").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text(role.getRoleName()).color(role.getColor()));
            return;
        }

        if(PlotIdentifier.isMemberOfPlot(player.getLocation().getBlockX(), player.getLocation().getBlockZ(), player.getUniqueId())){
            PlotRoles role = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId());
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Rang: ").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text(role.getRoleName()).color(role.getColor()));
        }

    }
}
