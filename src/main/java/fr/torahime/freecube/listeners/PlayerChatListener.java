package fr.torahime.freecube.listeners;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.utils.PlotIdentifier;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        int plotId = PlotIdentifier.getPlotIndex(player.getLocation());
        TextComponent message = (TextComponent) event.message();

        if(message.content().charAt(0) == '@') {
            for(Player p : player.getWorld().getPlayers()){
                if(GamePlayer.getPlayer(p.getUniqueId()).isGeneralChatActive()){
                    p.sendMessage(Component.text("Joueur " + player.getName()).color(NamedTextColor.GRAY)
                            .append(Component.text(": ").color(NamedTextColor.WHITE))
                            .append(Component.text("@").color(NamedTextColor.AQUA))
                            .append(Component.text(message.content().substring(1)).color(NamedTextColor.WHITE)));
                }
            }

            event.setCancelled(true);
            return;
        }

        if(PlotIdentifier.isInPlot(player.getLocation())) {

            for (Player p : player.getWorld().getPlayers()) {
                if (plotId == PlotIdentifier.getPlotIndex(p.getLocation())) {
                    p.sendMessage(Component.text("[").color(NamedTextColor.GOLD)
                            .append(Component.text("Joueur " + player.getName()).color(NamedTextColor.GRAY))
                            .append(Component.text(" -> ").color(NamedTextColor.GOLD))
                            .append(Component.text(plotId == 0 ? "Spawn" : String.valueOf(plotId)).color(NamedTextColor.GRAY))
                            .append(Component.text("] ").color(NamedTextColor.GOLD))
                            .append(message.color(NamedTextColor.YELLOW)));
                }
            }

        } else {

            for (Player p : player.getWorld().getPlayers()) {
                if (!PlotIdentifier.isInPlot(p.getLocation())) {
                    p.sendMessage(Component.text("[").color(NamedTextColor.GOLD)
                            .append(Component.text("Joueur " + player.getName()).color(NamedTextColor.GRAY))
                            .append(Component.text(" -> ").color(NamedTextColor.GOLD))
                            .append(Component.text("Route").color(NamedTextColor.GRAY))
                            .append(Component.text("] ").color(NamedTextColor.GOLD))
                            .append(message.color(NamedTextColor.YELLOW)));
                }
            }

        }

        event.setCancelled(true);

    }
}
