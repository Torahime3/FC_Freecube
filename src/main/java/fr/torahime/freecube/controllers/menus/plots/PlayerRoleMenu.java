package fr.torahime.freecube.controllers.menus.plots;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.naming.NamingEnumeration;

public class PlayerRoleMenu extends Menu {

    private final OfflinePlayer target;
    private final Plot plot;

    public PlayerRoleMenu(Player player, OfflinePlayer target, Plot plot, Menu lastMenu) {
        super(player, Component.text("Rôle de " + target.getName()), 54, lastMenu);
        this.target = target;
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        //Target head
        ItemBuilder targetHead = new ItemBuilder(Material.PLAYER_HEAD);
        targetHead.setDisplayName(Component.text("Rôle de " + target.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

        ItemBuilder kickButton = new ItemBuilder(Material.BARRIER);
        kickButton.setDisplayName(Component.text("Virer de la zone").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
        kickButton.setLore(Component.empty(), Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour virer de la zone").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),
                Component.text("(Accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));

        PlotRoles targetRole = plot.getMemberRole(target.getUniqueId());

        //Add role buttons
        int woolIndex = 33;
        for(PlotRoles role : PlotRoles.getAllRolesExclude(PlotRoles.GUEST)){

            ItemBuilder roleButton = new ItemBuilder(role.getMaterial());

            roleButton.setDisplayName(Component.text(role.getRoleName()).decoration(TextDecoration.ITALIC, false).color(role.getColor()).append(targetRole == role ? Component.text(" (Sélectionné)").color(NamedTextColor.AQUA) : Component.empty()));
            roleButton.setLore(Component.text("Permissions dans la zone :").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA));
            for(int i = 0; i < role.getPermissionsLiteral().length; i++){
                roleButton.addLore(Component.text("- " + role.getPermissionsLiteral()[i]).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE));
            }

            if(role == PlotRoles.CHIEF){

                this.addItem(roleButton.getItem(), woolIndex);

            } else {

                roleButton.addLore(Component.empty(), Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour donner ce rang").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),Component.text(role.getRoleCanBeGivenBy() == null ? "(Vous n'avez pas la permission de donner ce rang)" : "(Accessible au rang " + role.getRoleCanBeGivenBy().getRoleName() + ")").color(NamedTextColor.GRAY));
                this.addItem(roleButton.getItem(), woolIndex, () -> {

                    if(plot.getMemberRole(player.getUniqueId()) != role.getRoleCanBeGivenBy() && plot.getMemberRole(player.getUniqueId()) != PlotRoles.CHIEF){
                        this.player.sendMessage(Component.text("Vous n'avez pas la permission de donner ce rôle").color(NamedTextColor.RED));
                    } else {
                        plot.setMemberRole(target.getUniqueId(), role);
                        this.player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez donné le rôle de ").color(NamedTextColor.WHITE).append(Component.text(role.getRoleName()).color(role.getColor())).append(Component.text(" à " + target.getName()).color(NamedTextColor.WHITE))));
                        this.player.closeInventory();
                    }
                });
            }

            woolIndex--;
        }

        this.addItem(targetHead.getItem(), 13);
        this.addItem(kickButton.getItem(), 29, () -> {
            if(plot.getMemberRole(player.getUniqueId()) != PlotRoles.DEPUTY && plot.getMemberRole(player.getUniqueId()) != PlotRoles.CHIEF){
                this.player.sendMessage(Component.text("Vous n'avez pas la permission de virer ce joueur").color(NamedTextColor.RED));
            } else {
                plot.removePlayer(target.getUniqueId());
                this.player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez viré " + target.getName() + " de la zone").color(NamedTextColor.WHITE)));
                this.player.closeInventory();
            }
        });

        super.fillInventory();

    }

}
