package fr.torahime.freecube.controllers.menus.plots.settings.interaction;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.interactions.InteractionCategory;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.models.interactions.Interaction;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class InteractionsMenu extends Menu {

    private final Plot plot;

    private InteractionCategory category = InteractionCategory.PRIMARY;

    public InteractionsMenu(Player player, Plot plot, Menu lastMenu) {
        super(player, Component.text("Zone " + plot.getId() + " > Intéractions"), 54, lastMenu);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        this.inv.clear();

        int index = 0;
        for(Interaction interaction : Interaction.getAllInteractionsInCategory(category)){

            ItemBuilder interactionItem = new ItemBuilder(interaction.getMaterial());
            PlotStates interactionState = plot.getInteractions().get(interaction);
            TranslatableComponent itemName = Component.translatable(interactionItem.getItem().translationKey()).fallback(interaction.getMaterial().name());

            if(interactionState == PlotStates.ACTIVATE){
                interactionItem.getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                interactionItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                interactionItem.setDisplayName(itemName.color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
                interactionItem.addLore(Component.text("Intéractions ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                                .append(Component.text(interactionState.getState().substring(0, interactionState.getState().length() - 2).concat("ées").toLowerCase()).decoration(TextDecoration.ITALIC, false).color(interactionState.getColor()))
                                .append(Component.text(" avec ce bloc.")));

            } else {
                interactionItem.setDisplayName(itemName.color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
                interactionItem.addLore(Component.text("Intéractions ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY)
                                .append(Component.text(interactionState.getState().substring(0, interactionState.getState().length() - 2).concat("ées").toLowerCase()).decoration(TextDecoration.ITALIC, false).color(interactionState.getColor()))
                                .append(Component.text(" avec ce ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY)),Component.text("bloc (les associés/membres peuvent").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY),Component.text("toujours intéragir avec).").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
            }

            interactionItem.addLore(Component.empty(), Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + PlotStates.getInverseStateLiteral(interactionState).toLowerCase()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY) {
                this.addItem(interactionItem.getItem(), index, () -> {
                    plot.getInteractions().put(interaction, PlotStates.getInverseState(interactionState));
                    this.fillInventory();
                });
            } else {
                this.addItem(interactionItem.getItem(), index);
            }
            index++;
        }

        ItemBuilder nextCategoryItem = new ItemBuilder(Material.ARROW);
        nextCategoryItem.setDisplayName(Component.text("Catégorie suivante").color(NamedTextColor.GREEN));
        nextCategoryItem.addLore(Component.text("Clic gauche pour afficher la catégorie suivante").color(NamedTextColor.GRAY));

        this.addItem(nextCategoryItem.getItem(), this.inv.getSize() - 5, () -> {
            this.category = InteractionCategory.nextCategory(this.category);
            this.fillInventory();
        });

        super.fillInventory();

    }
}
