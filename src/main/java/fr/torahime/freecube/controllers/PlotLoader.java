package fr.torahime.freecube.controllers;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.services.plots.PlotService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class PlotLoader {

    public static void loadPlotFromGamePlayer(GamePlayer gp, Player player) {

        PlotService plotService = new PlotService();


        for (Integer plotId : gp.getPlotsIds()) {

            if(Plot.getPlot(plotId) != null) {
                player.sendMessage(Component.text("Plot " + plotId + " already loaded").color(NamedTextColor.RED));
                gp.addPlot(Plot.getPlot(plotId));
                continue;
            }

            Plot plot = plotService.get(String.valueOf(plotId));

            if(plot == null) {
                player.sendMessage(Component.text("Plot " + plotId + " not found in database").color(NamedTextColor.RED));
                continue;
            }

            Plot.claimPlot(plot.getId(), plot.getOwner(), plot);
            player.sendMessage(Component.text("Plot " + plotId + " loaded successfully").color(NamedTextColor.GREEN));
        }
    }
}
