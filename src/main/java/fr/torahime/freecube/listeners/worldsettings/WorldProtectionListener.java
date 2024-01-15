package fr.torahime.freecube.listeners.worldsettings;

import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.Objects;

public class WorldProtectionListener implements Listener {

    @EventHandler
    public void onPlayerForbiddenItemUse(PlayerInteractEvent event){
        if(event.getPlayer().isOp()) return;
        if(event.getItem() == null) return;

        for(ForbiddenItem forbiddenPlayerUseItem : ForbiddenItem.values()){
            if(event.getItem().getType() == forbiddenPlayerUseItem.getMaterial() && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)){
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
    public void onMonsterEggPlaced(PlayerInteractEvent event){
        if(event.getPlayer().isOp()) return;
        if(event.getItem() == null) return;
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getItemMeta() instanceof SpawnEggMeta) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispenserPlaceOutOfPlot(BlockDispenseEvent event){
        if(!PlotIdentifier.isInPlot(event.getVelocity().getBlockX(), event.getVelocity().getBlockZ())
                && (event.getItem().getType() == Material.LAVA_BUCKET
                || event.getItem().getType() == Material.WATER_BUCKET
                || event.getItem().getType() == Material.FIRE_CHARGE
                || event.getItem().getType() == Material.POWDER_SNOW_BUCKET
                || event.getItem().getType() == Material.FLINT_AND_STEEL
                || event.getItem().getType() == Material.COD_BUCKET
                || event.getItem().getType() == Material.SALMON_BUCKET
                || event.getItem().getType() == Material.PUFFERFISH_BUCKET
                || event.getItem().getType() == Material.TROPICAL_FISH_BUCKET
                || event.getItem().getType() == Material.AXOLOTL_BUCKET
                || event.getItem().getType() == Material.BUCKET
                || event.getItem().getType() == Material.MILK_BUCKET
                || event.getItem().getItemMeta() instanceof SpawnEggMeta)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySpawned(EntitySpawnEvent event){
        if(event.getEntity() instanceof LivingEntity){
            if(event.getEntity().getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND && event.getEntity().getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonExtendOutOfPlot(BlockPistonExtendEvent event){

        for (Block pushedBlock : event.getBlocks()) {
            Location newLocation = pushedBlock.getLocation().clone().add(event.getDirection().getModX(), event.getDirection().getModY(), event.getDirection().getModZ());
            if(!PlotIdentifier.isInPlot(newLocation)){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        Projectile p = event.getEntity();
        if(p instanceof AbstractArrow)
        {
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Freecube")), p::remove, 300);
        }

    }

    @EventHandler
    public void onVehicleGoingOutOfPlot(VehicleMoveEvent event){
        if(!PlotIdentifier.isInPlot(event.getTo())){
            event.getVehicle().teleport(event.getFrom());
        }
    }

    @EventHandler
    public void onVehicleGoingOutOfPlotWithPlayerIn(VehicleUpdateEvent event){
        if(!PlotIdentifier.isInPlot(event.getVehicle().getLocation())){
            event.getVehicle().eject();
            event.getVehicle().setVelocity(event.getVehicle().getVelocity().zero());

        }
    }

    @EventHandler
    public void onChickenEggThrown(PlayerEggThrowEvent event){
        event.setHatching(false);
    }

    @EventHandler
    public void onWaterLavaSpread(BlockFromToEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerGoThroughPortal(PlayerPortalEvent event){
        event.setCancelled(true);
    }

    //Structure: tree, mushroom, ...
    @EventHandler
    public void onStructureGrowOutsidePlot(StructureGrowEvent event){
        for(BlockState block : event.getBlocks()){
            if(!PlotIdentifier.isInPlot(block.getLocation())){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onItemChangedArmorStand(PlayerArmorStandManipulateEvent event){
            cancelEvent(event.getPlayer(), event.getRightClicked().getLocation().getBlock(), event);
    }

    @EventHandler
    public void onPlayerDestroyArmorStand(EntityDamageByEntityEvent event){
        if(event.getEntity().getType() != EntityType.ARMOR_STAND) return;
        cancelEvent((Player) event.getDamager(), event.getEntity().getLocation().getBlock(), event);
    }

    @EventHandler
    public void onSuspiciousGravelSandGetBrushed(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;

        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BRUSH) {
            cancelEvent(event.getPlayer(), event.getClickedBlock(), event);
        }
    }

    @EventHandler
    public void onFireArrowIgniteTnt(EntityChangeBlockEvent event){

        if(event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();
            if (event.getEntity().getType() == EntityType.ARROW && event.getBlock().getType() == Material.TNT_MINECART) {
                cancelEvent((Player) Objects.requireNonNull(arrow.getShooter()), event.getBlock(), event);
            }
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
    public void onPlayerFertilizeOnRoad(BlockFertilizeEvent event){
        if(event.getPlayer() == null) return;

        for(BlockState block : event.getBlocks()){
            if(!PlotIdentifier.isInPlot(block.getLocation())){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBucketPlaced(PlayerBucketEmptyEvent event){
            cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler
    public void onBucketFilled(PlayerBucketFillEvent event){
            cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    //Entity: boat, minecart, item, arrow, ...
    @EventHandler
    public void onEntityPlaced(EntityPlaceEvent event){
        if(event.getPlayer() == null) return;
        cancelEvent(event.getPlayer(), event.getBlock(), event);
    }

    //Vehicle: boat, minecart, ...
    @EventHandler
    public void onEntityDestroy(VehicleDestroyEvent event){
        if(event.getAttacker() == null) return;
        cancelEvent((Player) event.getAttacker(), event.getVehicle().getLocation().getBlock(), event);
    }

    //Hanging entity: painting, item frame, ...
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
        if(blockY < 1){
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
