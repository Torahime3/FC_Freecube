package fr.torahime.freecube.controllers.menus.plots.settings;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.html.HTML;

public class HoursMenu extends Menu {

    private Plot plot;
    public HoursMenu(Player player, Plot plot) {
        super(player, Component.text("Zone " + plot.getId() + " > Heure"), 54);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        int index = 0;
        for(Hours hour : Hours.values()){

            ItemBuilder hourItem = new ItemBuilder(Material.LEATHER_CHESTPLATE, index == 0 ? 1 : index);
            hourItem.setDisplayName(Component.text(hour.getHour()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            hourItem.setLore(Component.text(hour.getTick()));

            ItemMeta hourmeta = hourItem.getItem().getItemMeta();
            //put a blue color on the leathe chestplate
            hourmeta.


            this.addItem(hourItem.getItem(), index, () -> {
                plot.setHour(hour);
                player.getWorld().setTime(plot.getHour().getTick());
            });

            index++;

        }

    }

}
