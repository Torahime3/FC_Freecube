package fr.torahime.freecube.controllers.menus.plots.settings;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.preferences.Preferences;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class PreferencesMenu extends Menu {

    private final Plot plot;

    public PreferencesMenu(Player player, Plot plot) {
        super(player, Component.text("Zone " + plot.getId() + " > Préférences"), 54);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        int index = 0;
        for(Preferences preference : Preferences.values()){

            PlotStates preferenceState = plot.getPreferences().get(preference);

            ItemBuilder preferenceItem = new ItemBuilder(preference.getMaterial());
            preferenceItem.setDisplayName(Component.text(preference.getTitle()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

            for(String lore: preference.getLore()){
                preferenceItem.addLore(Component.text(lore).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE));
            }

            preferenceItem.addLore(Component.empty(), Component.text("Actuellement: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(preferenceState.getState()).decoration(TextDecoration.ITALIC, false).color(preferenceState.getColor())));
            if(plot.isPlayerPresent(player.getUniqueId()) && (plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY)) {
                preferenceItem.addLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + PlotStates.getInverseStateLiteral(preferenceState).toLowerCase()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));
            }

            this.addItem(preferenceItem.getItem(), index, () -> {
                if(plot.isPlayerPresent(player.getUniqueId()) && (plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY)){
                    plot.getPreferences().replace(preference, PlotStates.getInverseState(preferenceState));
                    this.fillInventory();
                }

            });
            index++;
        }

    }

}
