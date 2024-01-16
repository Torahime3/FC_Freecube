package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.controllers.customEvents.PlotEnterEvent;
import fr.torahime.freecube.controllers.customEvents.PlotQuitEvent;
import fr.torahime.freecube.controllers.customEvents.PlotUpdateEvent;
import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.preferences.Preference;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PreferencesListener implements Listener{

    public boolean canApplyPreference(Player player){

        if(player.isOp()){
            return false;
        }

        return PlotIdentifier.isInPlot(player.getLocation())
                && PlotIdentifier.isPlotClaimed(player.getLocation())
                && !PlotIdentifier.isMemberOfPlot(player.getLocation(),player.getUniqueId());

    }

    @EventHandler
    public void onPlayerEnterPlotApplyPreference(PlotEnterEvent event){
        update(event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerUpdatePlotApplyPreference(PlotUpdateEvent event){
        update(event.getPlayer(), false);
    }

    public void update(Player player, boolean onEnter) {

        //APPLY MANADATORY PREFERENCES
        if(PlotIdentifier.isInPlot(player.getLocation()) && PlotIdentifier.isPlotClaimed(player.getLocation())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
            CraftPlayer craftPlayer = (CraftPlayer) player;

//            if(plot.getHour() != Hours.TWELVE) {
//
//                ClientboundSetTimePacket timePacket = new ClientboundSetTimePacket(plot.getHour().getTick(), plot.getHour().getTick(), false);
//                craftPlayer.getHandle().connection.send(timePacket);
//            }
//
//            if(plot.getWeather() == Weather.DOWNFALL ) {
//
//                ClientboundGameEventPacket weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 1);
//                craftPlayer.getHandle().connection.send(weatherPacket);
//                weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, 1);
//                craftPlayer.getHandle().connection.send(weatherPacket);
//
//            } else if(plot.getWeather() == Weather.CLEAR){
//
//                ClientboundGameEventPacket weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 1);
//                craftPlayer.getHandle().connection.send(weatherPacket);
//                weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, 0);
//                craftPlayer.getHandle().connection.send(weatherPacket);
//
//            }

            if(onEnter && !plot.getMusicTransmitters().isEmpty()){
                GamePlayer gamePlayer = GamePlayer.getPlayer(player.getUniqueId());
                gamePlayer.playAllSoundsOfPlot(plot);
            }
        }

        //APPLY OPTIONNAL PREFERENCES
        if(canApplyPreference(player)) {
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

            //FLY
            player.setAllowFlight(!plot.getPreferences().get(Preference.FLY).getCancelEvent());

            //CLEAR
            if (!plot.getPreferences().get(Preference.CLEARINVENTORY).getCancelEvent()) {
                player.getInventory().clear();
            }

            //GAMEMODE
            player.setGameMode(plot.getPreferences().get(Preference.GAMEMODE).getGameMode());

            //SPAWNTP
            if (!plot.getPreferences().get(Preference.SPAWNTP).getCancelEvent()) {
                player.teleport(plot.getSpawn());
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez été téléporté au spawn de la zone en y entrant.").color(NamedTextColor.WHITE)));
            }

        } else {

                player.setAllowFlight(true);
                player.setGameMode(GameMode.CREATIVE);
        }

    }

    @EventHandler
    public void onPlayerQuitPlotResetPreference(PlotQuitEvent event){

        Player player = event.getPlayer();
        player.setAllowFlight(true);
        player.setGameMode(GameMode.CREATIVE);
        player.stopAllSounds();

        CraftPlayer craftPlayer = (CraftPlayer) player;

        ClientboundSetTimePacket timePacket = new ClientboundSetTimePacket(6000, 6000, false);
        craftPlayer.getHandle().connection.send(timePacket);

        ClientboundGameEventPacket weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 1);
        craftPlayer.getHandle().connection.send(weatherPacket);
        weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, 0);
        craftPlayer.getHandle().connection.send(weatherPacket);

    }

    //Player Drop Items Preference
    @EventHandler
    public void onPlayerDropOnPlotPreference(PlayerDropItemEvent event){

        if(canApplyPreference(event.getPlayer())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getPlayer().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preference.DROPITEMS).getCancelEvent());

        }

    }

    //Player Loot Items Preference
    @EventHandler
    public void onPlayerLootOnPlotPreference(EntityPickupItemEvent event){


        if(event.getEntity() instanceof Player && canApplyPreference((Player) event.getEntity())){

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(event.getEntity().getLocation()));
            event.setCancelled(plot.getPreferences().get(Preference.LOOTITEMS).getCancelEvent());

        }

    }


}
