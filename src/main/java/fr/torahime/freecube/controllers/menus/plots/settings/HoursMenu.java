package fr.torahime.freecube.controllers.menus.plots.settings;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import javax.swing.text.html.HTML;

public class HoursMenu extends Menu {

    private final Plot plot;
    public HoursMenu(Player player, Plot plot) {
        super(player, Component.text("Zone " + plot.getId() + " > Heure"), 54);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        int index = 0;
        for(Hours hour : Hours.values()){

            ItemBuilder hourItem = new ItemBuilder(Material.LEATHER_CHESTPLATE, index == 0 ? 1 : index);
            hourItem.setColor(hour.getRgb());
            hourItem.setDisplayName(Component.text(hour.getHour()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            hourItem.setLore(Component.empty(), Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Cliquez pour changer l'heure").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(plot.getHour() == hour){
                hourItem.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                hourItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
                this.addItem(hourItem.getItem(), index, () -> {
                    plot.setHour(hour);
                    player.getWorld().setTime(plot.getHour().getTick());
                    this.fillInventory();
                });
            } else {
                this.addItem(hourItem.getItem(), index);
            }


            index++;

        }

    }

}
