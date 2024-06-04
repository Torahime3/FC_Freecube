package fr.torahime.freecube.controllers.menus.plots.settings.pvp;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.areamaker.LocationType;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.models.pvp.PvpArea;
import fr.torahime.freecube.models.pvp.PvpWeapons;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
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
            int result = pa.setLocation(player.getLocation(), LocationType.A, plot);
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
                case -4:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La location ne peut pas être en dehors de la zone concernée").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(locationB.getItem(), 42, () -> {
            int result = pa.setLocation(player.getLocation(), LocationType.B, plot);
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
                case -4:
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La location ne peut pas être en dehors de la zone concernée").color(NamedTextColor.RED)));
                    break;
            }
        });

        this.addItem(deletePvpArea.getItem(), 0, () -> {
            plot.removePvpArea(pa);
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La zone pvp a été supprimé.").color(NamedTextColor.WHITE)));
            player.getInventory().close();
        });

        if(pa.isValid()){

            int index = 11;
            for(PvpWeapons pvpWeapon : PvpWeapons.values()){
                ItemBuilder weapon = new ItemBuilder(pvpWeapon.getMaterial());
                PlotStates weaponState = pa.getPvpWeaponsMap().get(pvpWeapon);

                if(weaponState == PlotStates.ACTIVATE){
                    weapon.getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                    weapon.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }

                weapon.setDisplayName(Component.text(pvpWeapon.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
                weapon.setLore(Component.text("Actuellement: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW)
                        .append(Component.text(weaponState.getState().substring(0, weaponState.getState().length() - 2).concat("és").toLowerCase()).decoration(TextDecoration.ITALIC, false).color(weaponState.getColor())),
                        Component.empty(),
                        Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE))
                                .append(Component.text(PlotStates.getInverseStateLiteral(weaponState).toLowerCase()).decoration(TextDecoration.ITALIC, false).color(PlotStates.getInverseState(weaponState).getColor())));

                this.addItem(weapon.getItem(), index, () -> {
                    pa.getPvpWeaponsMap().put(pvpWeapon, PlotStates.getInverseState(weaponState));
                    this.fillInventory();

                });

                if(index == 15) {
                    index = 20;
                }

                index++;
            }

            ItemBuilder glowArea = new ItemBuilder(Material.GLOWSTONE_DUST);
            glowArea.setDisplayName(Component.text("Surbrillance de la zone").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            glowArea.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour faire apparaître en surbrillance la zone").color(NamedTextColor.WHITE)));

            this.addItem(glowArea.getItem(), 49, () -> {
                pa.startParticleDisplay(player);
                player.closeInventory();
            });

        }

        super.fillInventory();
    }

}
