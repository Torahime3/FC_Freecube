package fr.torahime.freecube.models.preferences;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import javax.inject.Named;

public class PreferencesListener implements Listener{

    //TODO: Optimise this class with a better system if I find one

    public boolean canApplyPreference(Player player){

//        if(player.isOp()){
//            return false;
//        }

        return PlotIdentifier.isInPlot(player.getLocation())
                && PlotIdentifier.isPlotClaimed(player.getLocation())
                && !PlotIdentifier.isMemberOfPlot(player.getLocation(),player.getUniqueId());

    }

    @EventHandler
    public void onPlayerQuitPlotResetPreference(PlayerMoveEvent event){

        Player player = event.getPlayer();
        if(!PlotIdentifier.isInPlot(player.getLocation())) {
            player.setAllowFlight(true);
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    /////////////////////////////////////////////////////////////

    //Player Fly Preference
    @EventHandler
    public void onPlayerFlyOnPlotPreference(PlayerMoveEvent event){

        if(canApplyPreference(event.getPlayer())){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            player.setAllowFlight(!plot.getPreferences().get(Preferences.FLY).getCancelEvent());

        }
    }

    //Player Clear Inventory Preference
    @EventHandler
    public void onPlayerClearInventoryOnPlotPreference(PlayerMoveEvent event){

        if(canApplyPreference(event.getPlayer()) && GamePlayer.getPlayer(event.getPlayer().getUniqueId()).canReveicePlotInfos()){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            if(!plot.getPreferences().get(Preferences.CLEARINVENTORY).getCancelEvent()){
                player.getInventory().clear();
                GamePlayer.getPlayer(player.getUniqueId()).setCanReceivePlotInfos(false);
            }

        }

    }
    //Player Spawn Teleport Preference
    @EventHandler
    public void onPlayerSpawnTeleportAndClearOnPlotPreference(PlayerMoveEvent event){

        if(canApplyPreference(event.getPlayer()) && GamePlayer.getPlayer(event.getPlayer().getUniqueId()).canReveicePlotInfos()){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            if(!plot.getPreferences().get(Preferences.SPAWNTP).getCancelEvent()){
                player.teleport(plot.getSpawn());
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez été téléporté au spawn de la zone en y entrant.").color(NamedTextColor.WHITE)));
            }
            if(!plot.getPreferences().get(Preferences.CLEARINVENTORY).getCancelEvent()){
                player.getInventory().clear();
            }
            GamePlayer.getPlayer(player.getUniqueId()).setCanReceivePlotInfos(false);

        }

    }

    //Player Gamemode Preference
    @EventHandler
    public void changePlayerGamemodePreference(PlayerMoveEvent event){

        if(canApplyPreference(event.getPlayer()) ){

            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            player.setGameMode(plot.getPreferences().get(Preferences.GAMEMODE).getGameMode());

        }

    }

    //Player Drop Items Preference
    @EventHandler
    public void onPlayerDropOnPlotPreference(PlayerDropItemEvent event){

        if(canApplyPreference(event.getPlayer())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getPlayer().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preferences.DROPITEMS).getCancelEvent());

        }

    }

    //TODO : Fix this event (working but not the best way)
    @EventHandler
    public void onPlayerLootOnPlotPreference(EntityPickupItemEvent event){


        if(event.getEntity() instanceof Player && canApplyPreference((Player) event.getEntity())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getEntity().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preferences.DROPITEMS).getCancelEvent());

        }

    }


}
