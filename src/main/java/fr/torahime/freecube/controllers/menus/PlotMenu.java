package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;


public class PlotMenu extends Menu {

    public PlotMenu(Player player) {
        super(player, Component.text(String.format("Mes zones (Chef: ?/%d)", GamePlayer.getPlayer(player.getUniqueId()).getPlots().size())), 54);
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

            this.addItem(plotItem.getItem(), i);
            i++;
        }
//        this.invName = Component.text(String.format("Mes zones (Chef: %d/%d)", chiefCount, GamePlayer.getPlayer(playerID).getPlots().size()));

    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (Arrays.asList(PlotRoles.getAllMaterials()).contains(item.getType())) {
            TextComponent itemName = (TextComponent) item.getItemMeta().displayName();
            if(itemName == null) return;
            int plotID = Integer.parseInt(itemName.content().split(" ")[1]);
            this.player.teleport(new Location(player.getWorld(), PlotIdentifier.getPlotCenterCoordinates(plotID)[0], 51, PlotIdentifier.getPlotCenterCoordinates(plotID)[1]));
        }
    }
}

