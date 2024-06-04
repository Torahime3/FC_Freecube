package fr.torahime.freecube.controllers.menus.plots.settings.entity;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.areamaker.LocationType;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.entitys.PlotEntity;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
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

        ItemBuilder deleteEntityGenerator = new ItemBuilder(Material.BARRIER);
        deleteEntityGenerator.setDisplayName(Component.text("Supprimer le générateur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
        deleteEntityGenerator.setLore(Component.empty(),
                Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour supprimer le générateur d'entitées.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

        ItemBuilder locationA = eg.getLocationItemBuilder(LocationType.A);
        ItemBuilder locationB = eg.getLocationItemBuilder(LocationType.B);

        this.addItem(locationA.getItem(), 38, () -> {
            int result = eg.setLocation(player.getLocation(), LocationType.A, plot);
            switch (result) {
                case 1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A a été définie.").color(NamedTextColor.WHITE)));
                    eg.startParticleDisplay(player);
                    this.fillInventory();
                    break;
                case -1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A ne peut pas être la même que la position B.").color(NamedTextColor.RED)));
                    break;
                case -2:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text(String.format("La zone est trop grande. ( >%s)", eg.getMAX_VOLUME())).color(NamedTextColor.RED)));
                    break;
                case -3:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location A is null, not set.").color(NamedTextColor.RED)));
                    break;
                case -4:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La location ne peut pas être en dehors de la zone concernée").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(locationB.getItem(), 42, () -> {
            int result = eg.setLocation(player.getLocation(), LocationType.B, plot);
            switch (result) {
                case 1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B a été définie.").color(NamedTextColor.WHITE)));
                    eg.startParticleDisplay(player);
                    this.fillInventory();
                    break;
                case -1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B ne peut pas être la même que la position A.").color(NamedTextColor.RED)));
                    break;
                case -2:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text(String.format("La zone est trop grande. ( >%s)", eg.getMAX_VOLUME())).color(NamedTextColor.RED)));
                    break;
                case -3:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location B is null, not set.").color(NamedTextColor.RED)));
                    break;
                case -4:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La location ne peut pas être en dehors de la zone concernée").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(deleteEntityGenerator.getItem(), 0, () -> {
            plot.removeEntityGenerator(eg);
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Le générateur d'entitées a été supprimé.").color(NamedTextColor.WHITE)));
            player.getInventory().close();
        });

        if(eg.isValid()){

            int index = 1;
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

            ItemBuilder glowArea = new ItemBuilder(Material.GLOWSTONE_DUST);
            glowArea.setDisplayName(Component.text("Surbrillance de la zone").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            glowArea.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour faire apparaître en surbrillance la zone").color(NamedTextColor.WHITE)));

            this.addItem(glowArea.getItem(), 49, () -> {
                eg.startParticleDisplay(player);
                player.closeInventory();
            });

        }



        super.fillInventory();
    }
}
