package fr.torahime.freecube.teams.scoreboard;

import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class MainBoard {

    public static void createScoreboard(Player player){
        Component scoreboardName = Component.text("Fun").decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD)
                .append(Component.text("Cube").decorate(TextDecoration.BOLD).color(NamedTextColor.WHITE))
                .append(Component.text(".net").decoration(TextDecoration.BOLD, false).color(NamedTextColor.WHITE));

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("plotboard", Criteria.DUMMY, scoreboardName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§l").setScore(4);
        objective.getScore("§a").setScore(0);


//        Create role plot team
        Team roleTeam = scoreboard.registerNewTeam("role");
        String roleTeamKey = "§r";
        roleTeam.addEntry(roleTeamKey);
        objective.getScore(roleTeamKey).setScore(2);

        //Create plot team
        Team plotIdTeam = scoreboard.registerNewTeam("plotid");
        String plotTeamKey = "§k";
        plotIdTeam.addEntry(plotTeamKey);
        objective.getScore(plotTeamKey).setScore(3);

        //Create name team
        Team plotNameTeam = scoreboard.registerNewTeam("name");
        String plotNameTeamKey = "§n";
        plotNameTeam.addEntry(plotNameTeamKey);
        objective.getScore(plotNameTeamKey).setScore(1);

        player.setScoreboard(scoreboard);
    }

    public static void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        Team plotIdTeam = scoreboard.getTeam("plotid");
        Team roleTeam = scoreboard.getTeam("role");
        Team plotNameTeam = scoreboard.getTeam("name");

        if(!PlotIdentifier.isInPlot(player.getLocation())){
            plotIdTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text("Route").color(NamedTextColor.GRAY));
            roleTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            plotNameTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            plotNameTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            return;
        }

        if(PlotIdentifier.isInPlot(player.getLocation()) && PlotIdentifier.getPlotIndex(player.getLocation()) == 0){
            plotIdTeam.prefix(Component.text("Spawn").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("").color(NamedTextColor.YELLOW));
            roleTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            plotNameTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            plotNameTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            return;
        }

        if (PlotIdentifier.isInPlot(player.getLocation()) && !PlotIdentifier.isPlotClaimed(player.getLocation())) {
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Zone LIBRE").color(NamedTextColor.YELLOW));
            roleTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            plotNameTeam.prefix(Component.text("").color(NamedTextColor.GRAY));
            plotNameTeam.suffix(Component.text("").color(NamedTextColor.WHITE));
            return;
        }

        if(PlotIdentifier.isInPlot(player.getLocation()) && PlotIdentifier.isPlotClaimed(player.getLocation()) && !PlotIdentifier.isMemberOfPlot(player.getLocation().getBlockX(), player.getLocation().getBlockZ(), player.getUniqueId())){
            PlotRoles role = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId());
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Rang: ").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text(role.getRoleName()).color(role.getColor()));
            plotNameTeam.prefix(Component.text(Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getName()).color(NamedTextColor.YELLOW));
            return;
        }

        if(PlotIdentifier.isMemberOfPlot(player.getLocation().getBlockX(), player.getLocation().getBlockZ(), player.getUniqueId())){
            PlotRoles role = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getMemberRole(player.getUniqueId());
            plotIdTeam.prefix(Component.text("Zone: ").color(NamedTextColor.GRAY));
            plotIdTeam.suffix(Component.text(PlotIdentifier.getPlotIndex(player.getLocation())).color(NamedTextColor.WHITE));
            roleTeam.prefix(Component.text("Rang: ").color(NamedTextColor.GRAY));
            roleTeam.suffix(Component.text(role.getRoleName()).color(role.getColor()));
            plotNameTeam.prefix(Component.text(Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation())).getName()).color(NamedTextColor.YELLOW));
        }

    }
}
