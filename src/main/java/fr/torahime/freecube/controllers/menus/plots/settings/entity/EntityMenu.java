package fr.torahime.freecube.controllers.menus.plots.settings.entity;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.controllers.menus.plots.settings.music.MusicConfMenu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.musics.Music;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class EntityMenu extends Menu {

    Plot plot;

    public EntityMenu(Player player, Plot plot, Menu lastMenu){
        super(player, Component.text("Zone " + plot.getId() + " > Entités"), 54, lastMenu);
        this.plot = plot;
    }

    @Override
    protected void fillInventory() {

        if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
            ItemBuilder bannerAddMember = new ItemBuilder(Material.GREEN_BANNER);
            bannerAddMember.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP),
                    new Pattern(DyeColor.GREEN, PatternType.BORDER)).applyPatterns();
            bannerAddMember.setDisplayName(Component.text("[+] ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                    .append(Component.text("Créer un nouveau générateur d'entités").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD)));
            bannerAddMember.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                            .append(Component.text("Clic gauche pour créer un générateur d'entités").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),Component.text("à la position où vous êtes.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE),
                    Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
            bannerAddMember.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            this.addItem(bannerAddMember.getItem(), 0, () -> {
                EntityGenerator eg = new EntityGenerator();
                plot.addEntityGenerator(eg);
                new EntityConfMenu(player, plot, eg, this).openMenu();
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Le générateur d'entitées a été créé.").color(NamedTextColor.WHITE)));

            });

        }

        int index = 1;
        for(EntityGenerator eg : plot.getEntityGenerators()){
            ItemBuilder entityItem = new ItemBuilder(eg.getEntity().getSpawnEggMaterial());
            entityItem.setDisplayName(Component.text("Générateur de " + eg.getEntity().getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            entityItem.setLore(
                    Component.text("Position A:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(eg.isValidA() ? " X:" + eg.getA_X() + " Y:" + eg.getA_Y() + " Z:" + eg.getA_Z() : " Position A non définie.").color(NamedTextColor.WHITE)),
                    Component.text("Position B:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(eg.isValidB() ? " X:" + eg.getB_X() + " Y:" + eg.getB_Y() + " Z:" + eg.getB_Z() : " Position B non définie.").color(NamedTextColor.WHITE)),
                    Component.text("Taille de la zone: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(eg.getTotalBlocks() + " blocs.").color(NamedTextColor.WHITE)),
                    Component.empty(),
                    Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour configurer le générateur d'entités.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));


            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
                this.addItem(entityItem.getItem(), index, () -> {
                    new EntityConfMenu(player, plot, eg, this).openMenu();
                });
            } else {
                this.addItem(entityItem.getItem(), index);
            }

            index++;
        }

        super.fillInventory();
    }
}
