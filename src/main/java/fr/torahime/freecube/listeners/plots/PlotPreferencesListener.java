package fr.torahime.freecube.listeners.plots;

import fr.torahime.freecube.controllers.custom_events.PlotEnterEvent;
import fr.torahime.freecube.controllers.custom_events.PlotQuitEvent;
import fr.torahime.freecube.controllers.custom_events.PlotUpdateEvent;
import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.preferences.Preference;
import fr.torahime.freecube.models.weather.Weather;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlotPreferencesListener implements Listener{

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
        if(Plot.getPlot(PlotIdentifier.getPlotIndex(event.getPlayer().getLocation())) == null){
            return;
        }

        updateWeather(event.getPlayer());
        updatePreferences(event.getPlayer());

    }

    @EventHandler
    public void onPlayerUpdatePlotApplyPreference(PlotUpdateEvent event){
        if(Plot.getPlot(PlotIdentifier.getPlotIndex(event.getPlayer().getLocation())) == null){
            return;
        }

        updateWeather(event.getPlayer());
        updatePreferences(event.getPlayer(), false);
    }

    public void updatePreferences(Player player){
        updatePreferences(player, true);
    }

    public void updatePreferences(Player player, boolean isEnteringPlot){

        if(canApplyPreference(player)) {
            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

            //FLY
            player.setAllowFlight(!plot.getPreferences().get(Preference.FLY).getCancelEvent());

            //GAMEMODE
            player.setGameMode(plot.getPreferences().get(Preference.GAMEMODE).getGameMode());

            if(isEnteringPlot){

                //CLEAR
                if (!plot.getPreferences().get(Preference.CLEARINVENTORY).getCancelEvent()) {
                    player.getInventory().clear();
                }

                //SPAWNTP
                if (!plot.getPreferences().get(Preference.SPAWNTP).getCancelEvent()) {
                    player.teleport(plot.getSpawn());
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez été téléporté au spawn de la zone en y entrant.").color(NamedTextColor.WHITE)));
                }

            }

        } else {

                player.setAllowFlight(true);
                player.setGameMode(GameMode.CREATIVE);
        }
    }

    public void updateWeather(Player player){

        Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));
        CraftPlayer craftPlayer = (CraftPlayer) player;

        ClientboundSetTimePacket timePacket = new ClientboundSetTimePacket(plot.getHour().getTick(), plot.getHour().getTick(), false);
        craftPlayer.getHandle().connection.send(timePacket);

        if(plot.getWeather() == Weather.DOWNFALL ) {

            ClientboundGameEventPacket weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 1);
            craftPlayer.getHandle().connection.send(weatherPacket);
            weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, 1);
            craftPlayer.getHandle().connection.send(weatherPacket);

        } else if(plot.getWeather() == Weather.CLEAR){

            ClientboundGameEventPacket weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 1);
            craftPlayer.getHandle().connection.send(weatherPacket);
            weatherPacket = new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, 0);
            craftPlayer.getHandle().connection.send(weatherPacket);

        }

    }

    @EventHandler
    public void onPlayerQuitPlotResetPreference(PlotQuitEvent event){

        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getPlayer(player.getUniqueId());
        player.setAllowFlight(true);
        player.setGameMode(GameMode.CREATIVE);
        player.stopAllSounds();
        gamePlayer.stopAllSounds();

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
