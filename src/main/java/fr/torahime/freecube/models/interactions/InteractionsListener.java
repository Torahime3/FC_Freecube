package fr.torahime.freecube.models.interactions;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionsListener implements Listener {

    public boolean canApplyInteractions(Player player){

//        if(player.isOp()){
//            return false;
//        }

        return PlotIdentifier.isInPlot(player.getLocation())
                && PlotIdentifier.isPlotClaimed(player.getLocation())
                && !PlotIdentifier.isMemberOfPlot(player.getLocation(),player.getUniqueId());

    }
    @EventHandler
    public void onPlayerInteractOnPlot(PlayerInteractEvent event){

        if(canApplyInteractions(event.getPlayer())){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            Block block = event.getClickedBlock();

            if(block == null){
                return;
            }
            for(Interactions interaction : Interactions.values()){
                if(interaction.getMaterial() == block.getType()){
                    if(plot.getInteractions().get(interaction) == PlotStates.DEACTIVATE){
                        player.sendMessage(Component.text("Intéractions désactivées avec ce bloc.").color(NamedTextColor.DARK_RED));
                        event.setCancelled(plot.getInteractions().get(interaction) == PlotStates.DEACTIVATE);
                    }
                }
            }

        }



    }

}
