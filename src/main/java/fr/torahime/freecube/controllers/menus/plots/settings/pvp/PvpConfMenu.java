package fr.torahime.freecube.controllers.menus.plots.settings.pvp;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.LocationType;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.entitys.EntityGenerator;
import fr.torahime.freecube.models.pvp.PvpArea;
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

public class PvpConfMenu extends Menu {

    PvpArea pa;
    Plot plot;

    PvpConfMenu(Player player, Plot plot, PvpArea pvpArea, Menu lastMenu){
        super(player, Component.text("Zone " + plot.getId() + " > Pvp > Créer zone"), 54, lastMenu);
        this.plot = plot;
        this.pa = pvpArea;
    }

    @Override
    public void fillInventory(){

        ItemBuilder deletePvpArea = new ItemBuilder(Material.BARRIER);
        deletePvpArea.setDisplayName(Component.text("Supprimer la zone pvp").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
        deletePvpArea.setLore(Component.empty(),
                Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour supprimer le générateur d'entitées.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

        ItemBuilder locationA = pa.getLocationItemBuilder(LocationType.A);
        ItemBuilder locationB = pa.getLocationItemBuilder(LocationType.B);

        this.addItem(locationA.getItem(), 38, () -> {
            int result = pa.setLocationA(player.getLocation());
            switch (result) {
                case 1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A a été définie.").color(NamedTextColor.WHITE)));
                    pa.startParticleDisplay(player);
                    this.fillInventory();
                    break;
                case -1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A ne peut pas être la même que la position B.").color(NamedTextColor.RED)));
                    break;
                case -2:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text(String.format("La zone est trop grande. ( >%s)", pa.getMAX_VOLUME())).color(NamedTextColor.RED)));
                    break;
                case -3:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location A is null, not set.").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(locationB.getItem(), 42, () -> {
            int result = pa.setLocationB(player.getLocation());
            switch (result) {
                case 1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B a été définie.").color(NamedTextColor.WHITE)));
                    pa.startParticleDisplay(player);
                    this.fillInventory();
                    break;
                case -1:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position B ne peut pas être la même que la position A.").color(NamedTextColor.RED)));
                    break;
                case -2:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text(String.format("La zone est trop grande. ( >%s)", pa.getMAX_VOLUME())).color(NamedTextColor.RED)));
                    break;
                case -3:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location B is null, not set.").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(deletePvpArea.getItem(), 0, () -> {
            plot.removePvpArea(pa);
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La zone pvp a été supprimé.").color(NamedTextColor.WHITE)));
            player.getInventory().close();
        });

        if(pa.isValid()){

            ItemBuilder meleeWeapon = new ItemBuilder(Material.IRON_SWORD);
            meleeWeapon.setDisplayName(Component.text("Pvp en mêlée").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text(pa.isMeleeWeapon() ? " (Activé)" : "").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
            meleeWeapon.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour activer/désactiver le pvp en mêlée").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(pa.isMeleeWeapon()){
                meleeWeapon.addUnsafeEnchant(Enchantment.DURABILITY, 1);
                meleeWeapon.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            ItemBuilder rangeWeapon = new ItemBuilder(Material.BOW);
            rangeWeapon.setDisplayName(Component.text("Pvp à distance").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text(pa.isRangeWeapon() ? " (Activé)" : "").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
            rangeWeapon.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour activer/désactiver le pvp à distance").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(pa.isRangeWeapon()){
                rangeWeapon.addUnsafeEnchant(Enchantment.DURABILITY, 1);
                rangeWeapon.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            ItemBuilder glowArea = new ItemBuilder(Material.GLOWSTONE_DUST);
            glowArea.setDisplayName(Component.text("Surbrillance de la zone").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            glowArea.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour faire apparaître en surbrillance la zone").color(NamedTextColor.WHITE)));

            this.addItem(meleeWeapon.getItem(), 12, () -> {
                pa.setMeleeWeapon(!pa.isMeleeWeapon());
                fillInventory();
            });

            this.addItem(rangeWeapon.getItem(), 14, () -> {
                pa.setRangeWeapon(!pa.isRangeWeapon());
                fillInventory();
            });

            this.addItem(glowArea.getItem(), 40, () -> {
                pa.startParticleDisplay(player);
                player.closeInventory();
            });

        }

        super.fillInventory();
    }

}
