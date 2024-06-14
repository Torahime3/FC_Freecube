package fr.torahime.freecube.listeners.players;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.utils.PlotIdentifier;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        GamePlayer gp = GamePlayer.getPlayer(player.getUniqueId());
        int plotId = PlotIdentifier.getPlotIndex(player.getLocation());
        TextComponent message = (TextComponent) event.message();

        if(message.content().charAt(0) == '@') {
            for(Player p : player.getWorld().getPlayers()){
                if(GamePlayer.getPlayer(p.getUniqueId()).isGeneralChatActive()){
                    p.sendMessage(Component.text(gp.getRank().getPrefix() + " " + player.getName()).color(gp.getRank().getColor())
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
                if (PlotIdentifier.isInPlot(p.getLocation()) && plotId == PlotIdentifier.getPlotIndex(p.getLocation())) {
                    sendMessageFcFormat(gp, player, p, plotId, message);
                }
            }

        } else {

            for (Player p : player.getWorld().getPlayers()) {
                if (!PlotIdentifier.isInPlot(p.getLocation())) {
                    sendMessageFcFormat(gp, player, p, -1, message);
                }
            }

        }

        event.setCancelled(true);

    }

    public static void sendMessageFcFormat(GamePlayer playerGp, Player player, Player target, int plotId, Component message){
        sendMessageFcFormat(playerGp, player, target, plotId, message, false);
    }

    public static void sendMessageFcFormat(GamePlayer playerGp, Player player, Player target, int plotId, Component message, boolean privateMessage){
        target.sendMessage(Component.text("[").color(NamedTextColor.GOLD)
                .append(Component.text( playerGp.getRank().getPrefix() + " " + player.getName()).color(playerGp.getRank().getColor()))
                .append(Component.text(" -> ").color(NamedTextColor.GOLD))
                .append(Component.text(privateMessage ? "Moi" : plotId == -1 ? "Route" : plotId == 0 ? "Spawn" : String.valueOf(plotId)).color(plotId == 0 ? NamedTextColor.YELLOW : NamedTextColor.GRAY))
                .append(Component.text("]").color(NamedTextColor.GOLD))
                .append(privateMessage ? Component.text("[R]").color(NamedTextColor.YELLOW).hoverEvent(Component.text("Répondre à ").color(NamedTextColor.WHITE).append(Component.text(player.getName()).color(NamedTextColor.AQUA))).clickEvent(ClickEvent.suggestCommand("/m " + player.getName() + " ")) : Component.empty())
                .append(Component.text(" ").append(privateMessage ? message.color(NamedTextColor.WHITE) : message.color(NamedTextColor.YELLOW))));


        if(privateMessage) {
            GamePlayer targetGamePlayer = GamePlayer.getPlayer(target.getUniqueId());
            targetGamePlayer.setLastPlayerWhoMessaged(player);
            player.sendMessage(Component.text("[").color(NamedTextColor.GOLD)
                    .append(Component.text("Moi").color(NamedTextColor.GRAY))
                    .append(Component.text(" -> ").color(NamedTextColor.GOLD))
                    .append(Component.text(targetGamePlayer.getRank().getPrefix() + " " + target.getName()).color(targetGamePlayer.getRank().getColor()))
                    .append(Component.text("]").color(NamedTextColor.GOLD))
                    .append(Component.text(" ").append(message.color(NamedTextColor.WHITE))));
        }
    }

}
