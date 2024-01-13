package fr.torahime.freecube.listeners.worldsettings;

import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class WorldProtectionListener implements Listener {

    @EventHandler
    public void onPlayerForbiddenItemUse(PlayerInteractEvent event){
        if(event.getPlayer().isOp()) return;
        if(event.getItem() == null) return;

        for(ForbiddenPlayerUseItem forbiddenPlayerUseItem : ForbiddenPlayerUseItem.values()){
            if(event.getItem().getType() == forbiddenPlayerUseItem.getMaterial()){
                if(forbiddenPlayerUseItem.canBeUsedInADispenser()){
                    event.getPlayer().sendMessage(Component.text("Item interdit, mais tu peux l'utiliser dans un dispenser!").color(NamedTextColor.RED));
                } else {
                    event.getPlayer().sendMessage(Component.text("Item interdit.").color(NamedTextColor.RED));
                }
                event.setCancelled(true);
                return;
            }
        }
    }


    @EventHandler
    public void onEntityEggSpawned(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if((event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.EGG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSuspiciousGravelSandGetBrushed(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;

        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BRUSH) {
            cancelEvent(event.getPlayer(), event.getClickedBlock(), event);
        }

    }

    @EventHandler
    public void onPlayerDestroyFarming(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;

        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND){
            cancelEvent(event.getPlayer(), event.getClickedBlock(), event);
        }
    }

    @EventHandler
    public void onPlayerDestroyTurtleEgg(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;

        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.TURTLE_EGG){
            cancelEvent(event.getPlayer(), event.getClickedBlock(), event);
        }
    }

    @EventHandler
    public void onPlayerFertilize(BlockFertilizeEvent event){
        if(event.getPlayer() == null) return;
        cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onBucketPlaced(PlayerBucketEmptyEvent event){
            cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onBucketFilled(PlayerBucketFillEvent event){
            cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onEntityPlaced(EntityPlaceEvent event){
        if(event.getPlayer() == null) return;
        cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onEntityDestroy(VehicleDestroyEvent event){
        if(event.getAttacker() == null) return;
        cancelEvent((Player) event.getAttacker(), event.getVehicle().getLocation().getBlock(), event);
    }

    @EventHandler
    public void onHangingEntityPlaced(HangingPlaceEvent event){
        if(event.getPlayer() == null) return;
        cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onHangingEntityDestroyed(HangingBreakByEntityEvent event){
        cancelEvent((Player) event.getRemover(), event.getEntity().getLocation().getBlock(), event);
    }

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
