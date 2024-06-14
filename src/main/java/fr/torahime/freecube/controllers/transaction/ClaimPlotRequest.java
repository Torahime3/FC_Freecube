package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class ClaimPlotRequest extends Request{

    private final int plotIndex;

    public ClaimPlotRequest(Player sender, int plotIndex){
        super(sender, sender, 60);
        this.plotIndex = plotIndex;
    }

    @Override
    public void sendRequest(){
        super.sendRequest();

        receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Veux-tu vraiment obtenir cette zone ? Tu ne pourras pas la supprimer par la suite.").color(NamedTextColor.WHITE))
                .append(Component.text("\n[Clique ICI pour obtenir cette zone]").color(NamedTextColor.AQUA)
                .hoverEvent(HoverEvent.showText(Component.text("Oui, obtenir cette zone").color(NamedTextColor.WHITE)))
                .clickEvent(ClickEvent.runCommand("/fc accept " + this.id))));

    }


    @Override
    public void acceptRequestAsync() {
        new Thread(this::acceptRequest).start();
    }

    @Override
    public void acceptRequest(){

        if(Plot.claimPlot(plotIndex, receiver.getUniqueId()) == null){
            receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                    .append(Component.text("Une erreur s'est produite.").color(NamedTextColor.RED)));
            return;
        }

        GamePlayer.getPlayer(receiver.getUniqueId()).removeRequest(this);
        receiver.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                .append(Component.text("Tu as obtenu la zone. Amuses-toi bien !!").color(NamedTextColor.WHITE)));
    }

    public int getPlotIndex() {
        return plotIndex;
    }
}
