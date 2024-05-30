package fr.torahime.freecube.controllers.menus.plots.settings.pvp;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.game.Plot;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.roles.PlotRoles;
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

public class PvpMenu extends Menu {

    Plot plot;

    public PvpMenu(Player player, Plot plot, Menu lastMenu){
        super(player, Component.text("Zone " + plot.getId() + " > Pvp"), 54, lastMenu);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
            ItemBuilder bannerAddMember = new ItemBuilder(Material.GREEN_BANNER);
            bannerAddMember.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP),
                    new Pattern(DyeColor.GREEN, PatternType.BORDER)).applyPatterns();
            bannerAddMember.setDisplayName(Component.text("[+] ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                    .append(Component.text("Créer une nouvelle zone pvp").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD)));
            bannerAddMember.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                            .append(Component.text("Clic gauche pour créer un zone pvp").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),Component.text("à la position où vous êtes.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE),
                    Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
            bannerAddMember.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            this.addItem(bannerAddMember.getItem(), 0, () -> {
                PvpArea pvpArea = new PvpArea();
                plot.addPvpArea(pvpArea);
                new PvpConfMenu(player, plot, pvpArea, this).openMenu();
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La zone pvp a été créé.").color(NamedTextColor.WHITE)));
            });
        }

        int index = plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY ? 1 : 0;
        for(PvpArea pa : plot.getPvpAreas()){

            ItemBuilder diamondSword = new ItemBuilder(Material.DIAMOND_SWORD);
            diamondSword.setDisplayName(Component.text("Zone Pvp").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            diamondSword.setLore(
                    Component.text("Position A:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(pa.isValidA() ? " X:" + pa.getA_X() + " Y:" + pa.getA_Y() + " Z:" + pa.getA_Z() : " Position A non définie.").color(NamedTextColor.WHITE)),
                    Component.text("Position B:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(pa.isValidB() ? " X:" + pa.getB_X() + " Y:" + pa.getB_Y() + " Z:" + pa.getB_Z() : " Position B non définie.").color(NamedTextColor.WHITE)),
                    Component.text("Pvp Actif : ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(pa.isPvpEnabled() ? "Actif" : "Inactif").color(pa.isPvpEnabled() ? NamedTextColor.GREEN : NamedTextColor.RED)),
                    Component.text("Arme de mêlée : ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(pa.isMeleeWeapon() ? "Activée" : "Désactivée").color(pa.isMeleeWeapon() ? NamedTextColor.GREEN : NamedTextColor.RED)),
                    Component.text("Arme à distance : ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(pa.isRangeWeapon() ? "Activée" : "Désactivée").color(pa.isRangeWeapon() ? NamedTextColor.GREEN : NamedTextColor.RED)));

            if(pa.isPvpEnabled()){
                diamondSword.addUnsafeEnchant(Enchantment.DURABILITY, 1);
                diamondSword.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
                diamondSword.addLore(Component.empty(),
                        Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour configurer la zone pvp.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));


                this.addItem(diamondSword.getItem(), index, () -> {
                    new PvpConfMenu(player, plot, pa, this).openMenu();
                });
            } else {
                this.addItem(diamondSword.getItem(), index);
            }

            index++;
        }

        super.fillInventory();
    }
}
