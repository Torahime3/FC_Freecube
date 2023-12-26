package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class Request {

    private int id;
    private UUID sender;
    private UUID receiver;
    private int plotId;

//    private RequestType type;

    public Request(UUID sender, UUID receiver, int plotId) {
        this.id = new Random().nextInt(10000000);
        this.sender = sender;
        this.receiver = receiver;
        this.plotId = plotId;

    }

    public void sendRequest(){
        GamePlayer.getPlayer(receiver).addRequest(this);

        Player playerSender = Bukkit.getPlayer(sender);
        Player playerReceiver = Bukkit.getPlayer(receiver);

        if(playerSender == null || playerReceiver == null){
            return;
        }
        playerSender.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Tu as envoyé une invitation à").color(NamedTextColor.WHITE))
                .append(Component.text(" " + playerReceiver.getName() + " ").color(NamedTextColor.AQUA))
                .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                .append(Component.text(" " + plotId).color(NamedTextColor.AQUA)));

        playerReceiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Tu as reçu une invitation de").color(NamedTextColor.WHITE))
                .append(Component.text(" " + playerSender.getName() + " ").color(NamedTextColor.AQUA))
                .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                .append(Component.text(" " + plotId).color(NamedTextColor.AQUA))
                .append(Component.text("\n\n"))
                .append(Component.text("[Clique ici pour rejoindre la zone]").color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/fc accept " + this.id))));
    }

    public int getId() {
        return id;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public int getPlotId() {
        return plotId;
    }

}
