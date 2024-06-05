package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class InviteOnPlotRequest extends Request{

    private final int plotIndex;

    public InviteOnPlotRequest(Player sender, Player receiver, int plotId){
        super(sender, receiver, 60);
        this.plotIndex = plotId;
    }

    @Override
    public void sendRequest(){
        super.sendRequest();

        sender.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Tu as envoyé une invitation à").color(NamedTextColor.WHITE))
                .append(Component.text(" " + receiver.getName() + " ").color(NamedTextColor.AQUA))
                .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                .append(Component.text(" " + plotIndex).color(NamedTextColor.AQUA)));

        receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Tu as reçu une invitation de").color(NamedTextColor.WHITE))
                .append(Component.text(" " + sender.getName() + " ").color(NamedTextColor.AQUA))
                .append(Component.text("pour rejoindre la zone").color(NamedTextColor.WHITE))
                .append(Component.text(" " + plotIndex).color(NamedTextColor.AQUA))
                .append(Component.text("\n\n"))
                .append(Component.text("[Clique ici pour rejoindre la zone]").color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/fc accept " + this.id))));
    }

    @Override
    public void acceptRequestAsync() {
        new Thread(this::acceptRequest).start();
    }

    @Override
    public void acceptRequest(){

        Plot plot = Plot.getPlot(this.plotIndex);
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
