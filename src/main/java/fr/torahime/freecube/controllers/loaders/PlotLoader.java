package fr.torahime.freecube.controllers.loaders;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.services.plots.PlotService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class PlotLoader {

    public static void loadPlotFromGamePlayer(GamePlayer gp, Player player) {

        PlotService plotService = new PlotService();

        for (Integer plotId : gp.getPlotsIds()) {

            loadPlot(plotService, gp, player, plotId);
        }
    }

    public static Plot loadPlot(PlotService plotService, GamePlayer gp, Player player, int plotId){
        if(plotId == 0) return null;

        //Plot already loaded
        if(Plot.getPlot(plotId) != null) {
            gp.addPlot(Plot.getPlot(plotId));
            return null;
        }

        Plot plot = plotService.get(String.valueOf(plotId));

        //Plot not found in database
        if(plot == null) {
            return null;
        }


        //Load plot from the database
        Plot newPlot = Plot.claimPlot(plot.getId(), plot.getOwner(), plot);

        //Add plot in gameplayer if user who loaded the plot is not the owner
        if(!plot.getOwner().equals(player.getUniqueId())){
            gp.addPlot(newPlot);
        }

        return newPlot;
    }

    }
