package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class Request {

    private int id;
    private Player sender;
    private Player receiver;
    private int plotId;
    private RequestType type;

    public Request(Player sender, Player receiver, int plotId) {
        this.id = new Random().nextInt(10000000);
        this.sender = sender;
        this.receiver = receiver;
        this.plotId = plotId;
        this.type = RequestType.PLOT;
    }

    public void sendRequest(){

        GamePlayer.getPlayer(receiver.getUniqueId()).addRequest(this);

        if(sender == null || receiver == null){
            return;
        }

        if(type == RequestType.PLOT) {

            sender.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Tu as envoyé une invitation à").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + receiver.getName() + " ").color(NamedTextColor.AQUA))
                    .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + plotId).color(NamedTextColor.AQUA)));

            receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Tu as reçu une invitation de").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + sender.getName() + " ").color(NamedTextColor.AQUA))
                    .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + plotId).color(NamedTextColor.AQUA))
                    .append(Component.text("\n\n"))
                    .append(Component.text("[Clique ici pour rejoindre la zone]").color(NamedTextColor.GREEN)
                            .clickEvent(ClickEvent.runCommand("/fc accept " + this.id))));

        }
    }

    public void acceptRequest(){

        if(this.type == RequestType.PLOT){

            Plot plot = Plot.getPlot(this.plotId);
            plot.addPlayer(receiver.getUniqueId(), PlotRoles.ASSOCIATE);
            GamePlayer.getPlayer(receiver.getUniqueId()).removeRequest(this);

            sender.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Le joueur").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + receiver.getName() + " ").color(NamedTextColor.AQUA))
                    .append(Component.text("a rejoint la zone").color(NamedTextColor.WHITE))
                    .append(Component.text(" " + plot.getId()).color(NamedTextColor.AQUA)));

            receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Tu as").color(NamedTextColor.WHITE))
                    .append(Component.text(" rejoint ").color(NamedTextColor.GREEN))
                    .append(Component.text("la zone. Tu es actuellement").color(NamedTextColor.WHITE))
                    .append(Component.text(" Associé").color(NamedTextColor.AQUA))
                    .append(Component.text(", tu ne peux donc pas construire. \nLe joueur qui t'a invité doit te donner le rang de").color(NamedTextColor.WHITE))
                    .append(Component.text(" Membre ").color(NamedTextColor.AQUA))
                    .append(Component.text("pour que tu puisses construire.").color(NamedTextColor.WHITE)));
        }

    }

    public int getId() {
        return id;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public int getPlotId() {
        return plotId;
    }

}
