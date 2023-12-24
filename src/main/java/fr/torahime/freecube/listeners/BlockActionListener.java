package fr.torahime.freecube.listeners;

import fr.torahime.freecube.utils.PlotIdentifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockActionListener implements Listener {

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event){

        cancelEvent(event.getPlayer(), event.getBlock(), event);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        cancelEvent(event.getPlayer(), event.getBlock(), event);

    }

    public void cancelEvent(Player player, Block block, Cancellable event){

        if(player.isOp()) return;

        int blockX = block.getLocation().getBlockX();
        int blockY = block.getLocation().getBlockY();
        int blockZ = block.getLocation().getBlockZ();

        //Check minimun build Y
        if(blockY < 0){
            event.setCancelled(true);
        }

        //Check plot property
        if(!PlotIdentifier.isMemberOfPlot(blockX, blockZ, player.getUniqueId())){
            event.setCancelled(true);
        }

        //Check road property
        if(!PlotIdentifier.isInPlot(blockX, blockZ)){
            event.setCancelled(true);
        }
    }

}
