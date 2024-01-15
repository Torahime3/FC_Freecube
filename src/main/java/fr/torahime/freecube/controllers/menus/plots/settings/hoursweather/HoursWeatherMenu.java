package fr.torahime.freecube.controllers.menus.plots.settings.hoursweather;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.hours.Hours;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.models.weather.Weather;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class HoursWeatherMenu extends Menu {

    private final Plot plot;
    public HoursWeatherMenu(Player player, Plot plot, Menu lastMenu) {
        super(player, Component.text("Zone " + plot.getId() + " > Heure & Météo"), 54, lastMenu);
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
                hourItem.setDisplayName(Component.text(hour.getHour()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text(" (Actuel)").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
                hourItem.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                hourItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
                this.addItem(hourItem.getItem(), index, () -> {
                    plot.setHour(hour);
                    plot.updateAllPlayersOverPlot();
                    this.fillInventory();
                });
            } else {
                this.addItem(hourItem.getItem(), index);
            }
            index++;
        }

        index = 45;
        for(Weather weather : Weather.values()){

            ItemBuilder weatherItem = new ItemBuilder(weather.getMaterial());
            weatherItem.setDisplayName(Component.text(weather.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            weatherItem.setLore(Component.empty(), Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Cliquez pour changer la météo").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(plot.getWeather() == weather){
                weatherItem.setDisplayName(Component.text(weather.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text(" (Actuel)").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
                weatherItem.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                weatherItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
                this.addItem(weatherItem.getItem(), index, () -> {
                    plot.setWeather(weather);
                    plot.updateAllPlayersOverPlot();
                    this.fillInventory();
                });
            } else {
                this.addItem(weatherItem.getItem(), index);
            }
            index++;
        }

        super.fillInventory();

    }

}
