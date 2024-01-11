package fr.torahime.freecube.listeners;

import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
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
            return;
        }

        //Check road property
        if(!PlotIdentifier.isInPlot(blockX, blockZ)){
            event.setCancelled(true);
            return;
        }

        //Check plot property
        if(!PlotIdentifier.isMemberOfPlot(blockX, blockZ, player.getUniqueId())){
            player.sendMessage(Component.text("Tu ne peux pas construire ici.").color(NamedTextColor.RED)
                    .append(Component.text("\nSi tu souhaites construire, rends-toi dans l'une de tes zones, ou trouve une nouvelle zone: ").color(NamedTextColor.WHITE))
                    .append(Component.text("/fc claim").color(NamedTextColor.AQUA)));

            event.setCancelled(true);
        }

    }

}
