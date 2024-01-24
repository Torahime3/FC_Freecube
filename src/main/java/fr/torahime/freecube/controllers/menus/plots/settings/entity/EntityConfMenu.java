package fr.torahime.freecube.controllers.menus.plots.settings.entity;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.PlotStates;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.entitys.PlotEntity;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class EntityConfMenu extends Menu {

    Plot plot;
    EntityGenerator eg;

    public EntityConfMenu(Player player, Plot plot, EntityGenerator eg, Menu lastMenu) {
        super(player, Component.text("Zone " + plot.getId() + " > Entités > Créer zone"), 54, lastMenu);
        this.plot = plot;
        this.eg = eg;
    }

    @Override
    protected void fillInventory() {

            ItemBuilder locationA = new ItemBuilder(eg.isValidA() ? Material.GREEN_BANNER : Material.RED_BANNER);
            locationA.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
                    new Pattern(eg.isValidA() ? DyeColor.GREEN : DyeColor.RED, PatternType.BORDER)).applyPatterns();
            locationA.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
            locationA.setDisplayName(Component.text("Position A" + (eg.isValidA() ? " X:" + eg.getA_X() + " Y:" + eg.getA_Y() + " Z:" + eg.getA_Z() : "")).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            locationA.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour définir la position A").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            ItemBuilder locationB = new ItemBuilder(eg.isValidB() ? Material.GREEN_BANNER : Material.RED_BANNER);
            locationB.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
                    new Pattern(eg.isValidB() ? DyeColor.GREEN : DyeColor.RED, PatternType.CURLY_BORDER),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
                    new Pattern(eg.isValidB() ? DyeColor.GREEN : DyeColor.RED, PatternType.BORDER)).applyPatterns();
            locationB.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
            locationB.setDisplayName(Component.text("Position B" + (eg.isValidB() ? " X:" + eg.getB_X() + " Y:" + eg.getB_Y() + " Z:" + eg.getB_Z() : "")).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            locationB.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour définir la position B").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            this.addItem(locationA.getItem(), 38, () -> {
                if(eg.setLocationA(player.getLocation())){
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A a été définie.").color(NamedTextColor.WHITE)));
                    this.fillInventory();
                } else {
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A ne peut pas être la même que la position B.").color(NamedTextColor.RED)));
                }
            });

            this.addItem(locationB.getItem(), 42, () -> {
                if(eg.setLocationB(player.getLocation())){
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B a été définie.").color(NamedTextColor.WHITE)));
                    this.fillInventory();
                } else {
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B ne peut pas être la même que la position A.").color(NamedTextColor.RED)));
                }

            });

            if(eg.isValid()){

                int index = 0;
                for(PlotEntity entity : PlotEntity.values()){

                    ItemBuilder entityItem = new ItemBuilder(entity.getSpawnEggMaterial());
                    entityItem.setDisplayName(Component.text(entity.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

                    if(eg.getEntity() == entity){
                        entityItem.setDisplayName(Component.text(entity.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD)
                                .append(Component.text(" (Actuel)").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
                        entityItem.addUnsafeEnchant(Enchantment.DURABILITY, 1);
                        entityItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }

                    entityItem.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour définir l'entité").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

                    this.addItem(entityItem.getItem(), index, () -> {
                        eg.setEntity(entity);
                        player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("L'entité a été définie.").color(NamedTextColor.WHITE)));
                        this.fillInventory();
                    });

                    index++;

                }

                ItemBuilder changeFrequency = new ItemBuilder(Material.CLOCK);
                changeFrequency.setDisplayName(Component.text("Fréquence de spawn: " + eg.getFrequency().getState()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
                changeFrequency.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour changer la fréquence en " + PlotStates.getInverseStateLiteral(eg.getFrequency())).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

                this.addItem(changeFrequency.getItem(), 40, () -> {
                    eg.setFrequency(PlotStates.getInverseState(eg.getFrequency()));
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La fréquence de spawn a été définie sur " + eg.getFrequency().getState().toLowerCase() + ".").color(NamedTextColor.WHITE)));
                    this.fillInventory();
                });
            }



        super.fillInventory();
    }
}
