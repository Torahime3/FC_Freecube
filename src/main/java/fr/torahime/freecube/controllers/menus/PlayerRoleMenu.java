package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.naming.NamingEnumeration;

public class PlayerRoleMenu extends Menu {

    private final Player target;
    private final Plot plot;

    public PlayerRoleMenu(Player player, Player target, Plot plot) {
        super(player, Component.text("Rôle de " + target.getName()), 54);
        this.target = target;
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        //Target head
        ItemBuilder targetHead = new ItemBuilder(Material.PLAYER_HEAD);
        targetHead.setDisplayName(Component.text("Rôle de " + target.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

        //Add role buttons
        int woolIndex = 30;
        for(PlotRoles role : PlotRoles.getAllRolesExclude(PlotRoles.GUEST, PlotRoles.CHIEF)){

            ItemBuilder roleButton = new ItemBuilder(role.getMaterial());
            roleButton.setDisplayName(Component.text(role.getRoleName()).decoration(TextDecoration.ITALIC, false).color(role.getColor()));
            roleButton.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour donner le rôle de " + role.getRoleName() + " à " + target.getName()).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

            this.addItem(roleButton.getItem(), woolIndex, () -> {
                plot.setMemberRole(target.getUniqueId(), role);
                this.player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez donné le rôle de ").color(NamedTextColor.WHITE).append(Component.text(role.getRoleName()).color(role.getColor())).append(Component.text(" à " + target.getName()).color(NamedTextColor.WHITE))));
                this.player.closeInventory();
            });

            woolIndex++;
        }

        this.addItem(targetHead.getItem(), 13);

    }

}
