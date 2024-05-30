package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.models.game.Plot;
import fr.torahime.freecube.models.game.PlotStates;
import fr.torahime.freecube.models.interactions.Interaction;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlotInteractionsListener implements Listener {

    public boolean canApplyInteractions(Player player, PlayerInteractEvent event){

        if(player.isOp()){
            return false;
        }
        if(event.getClickedBlock() == null){
            return false;
        }

        return PlotIdentifier.isInPlot(event.getClickedBlock().getLocation())
                && PlotIdentifier.isPlotClaimed(event.getClickedBlock().getLocation())
                && !PlotIdentifier.isMemberOfPlot(event.getClickedBlock().getLocation(),player.getUniqueId());

    }
    @EventHandler
    public void onPlayerInteractOnPlot(PlayerInteractEvent event){

        if(canApplyInteractions(event.getPlayer(), event)){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            Block block = event.getClickedBlock();

            if(block == null){
                return;
            }
            for(Interaction interaction : Interaction.values()){
                if(interaction.getMaterial() == block.getType()){
                    if(plot.getInteractions().get(interaction) == PlotStates.DEACTIVATE){
                        player.sendMessage(Component.text("Intéractions désactivées avec ce bloc.").color(NamedTextColor.RED));
                        event.setCancelled(plot.getInteractions().get(interaction) == PlotStates.DEACTIVATE);
                    }
                }
            }

        }



    }

}
