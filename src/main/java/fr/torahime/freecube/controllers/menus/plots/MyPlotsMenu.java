package fr.torahime.freecube.controllers.menus.plots;

import fr.torahime.freecube.controllers.custom_events.PlotEnterEvent;
import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.models.game.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;


public class MyPlotsMenu extends Menu {

    public MyPlotsMenu(Player player) {
        super(player,
                !GamePlayer.getPlayer(player.getUniqueId()).getPlots().isEmpty() ? Component.text(String.format("Mes zones (Chef: %d/%d)", GamePlayer.getPlayer(player.getUniqueId()).getChefPlotsCount(), GamePlayer.getPlayer(player.getUniqueId()).getPlots().size())) : Component.text("Vous n'avez aucune zone"),
                54);
    }

    @Override
    public void fillInventory() {

        UUID playerID = this.player.getUniqueId();

        //Create buttons (items)
        int i = 0;
        for (Plot plot : GamePlayer.getPlayer(playerID).getPlots()) {

            PlotRoles plotRole = plot.getMemberRole(playerID);;

            ItemBuilder plotItem = new ItemBuilder(plotRole.getMaterial());
            plotItem.setDisplayName(Component.text(String.format("Zone %d", plot.getId())).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotItem.setLore(Component.text("Rang: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text(plotRole.getRoleName()).color(plotRole.getColor())).decoration(TextDecoration.ITALIC, false),
                    Component.text(""),
                    Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour se téléporter").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            this.addItem(plotItem.getItem(), i, () -> {
                this.player.teleport(new Location(player.getWorld(), PlotIdentifier.getPlotCenterCoordinates(plot.getId())[0], 51, PlotIdentifier.getPlotCenterCoordinates(plot.getId())[1]));
                Bukkit.getPluginManager().callEvent(new PlotEnterEvent(this.player));
            });
            i++;
        }

    }

}

