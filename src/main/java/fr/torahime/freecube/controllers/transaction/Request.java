package fr.torahime.freecube.controllers.transaction;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Request {

    protected static HashSet<Integer> ids = new HashSet<>();
    protected int id;
    protected Player sender;
    protected Player receiver;
    protected int lifeTime;
    protected int creationTime;

    public Request(Player sender, Player receiver, int lifeTime) {

        do {
            this.id = new Random().nextInt(1000000);
        } while (ids.contains(this.id));

        ids.add(this.id);
        this.sender = sender;
        this.receiver = receiver;
        this.lifeTime = lifeTime;
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
        }, this.lifeTime * 20L);

    }

    public void acceptRequestAsync() {
        new Thread(this::acceptRequest).start();
    }

    public abstract void acceptRequest();

    public int getId() {
        return id;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }


}
