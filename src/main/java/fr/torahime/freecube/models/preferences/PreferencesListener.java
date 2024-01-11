package fr.torahime.freecube.models.preferences;

import fr.torahime.freecube.controllers.events.PlotEnterEvent;
import fr.torahime.freecube.controllers.events.PlotQuitEvent;
import fr.torahime.freecube.controllers.events.PlotUpdateEvent;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

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
    public void onPlayerEnterPlotApplyPreference(PlotEnterEvent event){
        update(event.getPlayer());
    }

    @EventHandler
    public void onPlayerUpdatePlotApplyPreference(PlotUpdateEvent event){
        update(event.getPlayer());
    }

    public void update(Player player) {
        if(canApplyPreference(player)) {

//            Player player = event.getPlayer();
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

            //APPLY PREFERENCES

            //FLY
            player.setAllowFlight(!plot.getPreferences().get(Preferences.FLY).getCancelEvent());

            //CLEAR
            if (!plot.getPreferences().get(Preferences.CLEARINVENTORY).getCancelEvent()) {
                player.getInventory().clear();
            }

            //GAMEMODE
            player.setGameMode(plot.getPreferences().get(Preferences.GAMEMODE).getGameMode());

            if (!plot.getPreferences().get(Preferences.SPAWNTP).getCancelEvent()) {
                player.teleport(plot.getSpawn());
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez été téléporté au spawn de la zone en y entrant.").color(NamedTextColor.WHITE)));
            }
        }
    }

    @EventHandler
    public void onPlayerQuitPlotResetPreference(PlotQuitEvent event){

        Player player = event.getPlayer();
        player.setAllowFlight(true);
        player.setGameMode(GameMode.CREATIVE);

    }

    //Player Drop Items Preference
    @EventHandler
    public void onPlayerDropOnPlotPreference(PlayerDropItemEvent event){

        if(canApplyPreference(event.getPlayer())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getPlayer().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preferences.DROPITEMS).getCancelEvent());

        }

    }

    //Player Loot Items Preference
    @EventHandler
    public void onPlayerLootOnPlotPreference(EntityPickupItemEvent event){


        if(event.getEntity() instanceof Player && canApplyPreference((Player) event.getEntity())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getEntity().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preferences.LOOTITEMS).getCancelEvent());

        }

    }


}
