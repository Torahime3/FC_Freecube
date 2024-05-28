package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Request {

    private static ArrayList<Integer> ids = new ArrayList<>();
    private int id;
    private Player sender;
    private Player receiver;
    private int plotId;
    private RequestType type;
    private int creationTime;

    public Request(Player sender, Player receiver, int plotId, RequestType type) {

        do {
            this.id = new Random().nextInt(10000000);
        } while (ids.contains(this.id));

        ids.add(this.id);
        this.sender = sender;
        this.receiver = receiver;
        this.plotId = plotId;
        this.type = type;
        this.creationTime = (int) (System.currentTimeMillis() / 1000);
    }

    public void sendRequest(){

        if(sender == null || receiver == null){
            return;
        }

        GamePlayer.getPlayer(receiver.getUniqueId()).addRequest(this);

        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Freecube")), () -> {
            if(GamePlayer.getPlayer(receiver.getUniqueId()).getPendingRequests().contains(this)){
                GamePlayer.getPlayer(receiver.getUniqueId()).removeRequest(this);
                receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                        .append(Component.text("La demande a expiré.").color(NamedTextColor.RED)));
            }
        }, this.type.getLifetime() * 20L);

        if(type == RequestType.CLAIM){

            receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Veux-tu vraiment obtenir cette zone ? Tu ne pourras pas la supprimer par la suite.").color(NamedTextColor.WHITE))
                    .append(Component.text("\n[Clique ICI pour obtenir cette zone]").color(NamedTextColor.AQUA)
                            .hoverEvent(HoverEvent.showText(Component.text("Oui, obtenir cette zone").color(NamedTextColor.WHITE)))
                            .clickEvent(ClickEvent.runCommand("/fc accept " + this.id))));

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

        if(this.type == RequestType.CLAIM){
            if(Plot.claimPlot(plotId, receiver.getUniqueId()) == null){
                receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                        .append(Component.text("Une erreur s'est produite.").color(NamedTextColor.RED)));
                return;
            }

            GamePlayer.getPlayer(receiver.getUniqueId()).removeRequest(this);
            receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Tu as obtenu la zone. Amuses-toi bien !!").color(NamedTextColor.WHITE)));

        }

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
