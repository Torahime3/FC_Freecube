package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.controllers.menus.plots.PlayerRoleMenu;
import fr.torahime.freecube.controllers.menus.plots.PlotMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.PreferencesMenu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.roles.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.UUID;

public class MainMenu extends Menu {

    public MainMenu(Player player){
        super(player, Component.text(player.getName() + " - " + (PlotIdentifier.isInPlot(player.getLocation()) ? String.format("Zone %d", PlotIdentifier.getPlotIndex(player.getLocation())) : "Route")), 54);
    }

    @Override
    public void fillInventory(){

        //Create buttons (items)
        ItemBuilder spawn = new ItemBuilder(Material.BLUE_BANNER);
        spawn.setDisplayName(Component.text("Aller au spawn du monde").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        spawn.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour aller au spawn du monde").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        ItemBuilder myPlots = new ItemBuilder(Material.OAK_SIGN);
        myPlots.setDisplayName(Component.text("Mes Zones").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

        ItemBuilder freeZone = new ItemBuilder(Material.MAP);
        freeZone.setDisplayName(Component.text("Trouver une zone libre").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        freeZone.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour trouver une zone libre").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));


        // Add members heads and every info on the plot (if in plot)
        if(PlotIdentifier.isInPlot(this.player.getLocation()) && PlotIdentifier.isPlotClaimed(this.player.getLocation())) {

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

            ItemBuilder plotInfoGrassBlock = new ItemBuilder(Material.GRASS_BLOCK);
            plotInfoGrassBlock.setDisplayName(Component.text("Zone " + plot.getId()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            plotInfoGrassBlock.setLore(Component.text("Nom: ").color(NamedTextColor.WHITE).append(Component.text(plot.getName()).color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false),
                    Component.text("Membre(s): ").color(NamedTextColor.WHITE).append(Component.text(plot.getMembers().size()).color(NamedTextColor.AQUA)).decoration(TextDecoration.ITALIC, false));


            ItemBuilder plotSpawnSunflower = new ItemBuilder(Material.SUNFLOWER);
            plotSpawnSunflower.setDisplayName(Component.text("Spawn de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotSpawnSunflower.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour aller au spawn de la").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotPreferenceCraftTable = new ItemBuilder(Material.CRAFTING_TABLE);
            plotPreferenceCraftTable.setDisplayName(Component.text("Préférences de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotPreferenceCraftTable.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir") +  " les").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("préférences de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotHourClock = new ItemBuilder(Material.CLOCK);
            plotHourClock.setDisplayName(Component.text("Heure de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotHourClock.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir") +  " l'heure de la zone").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotInteractionsChest = new ItemBuilder(Material.CHEST);
            plotInteractionsChest.setDisplayName(Component.text("Interactions de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotInteractionsChest.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir") +  " les interactions de la zone").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY) {

                plotSpawnSunflower.addLore(Component.empty(), Component.text("Commandes pour changer le spawn:").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                        Component.text("/fc setspawn").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                        Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));

                plotPreferenceCraftTable.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotHourClock.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotInteractionsChest.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
            }

            this.addItem(plotInfoGrassBlock.getItem(), 18);
            this.addItem(plotSpawnSunflower.getItem(), 23, () -> {
                this.player.teleport(plot.getSpawn());
                this.player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                        .append(Component.text("Tu as été téléporté au spawn de la zone").color(NamedTextColor.WHITE)));
            });
            this.addItem(plotPreferenceCraftTable.getItem(), 24, () -> {
                new PreferencesMenu(this.player, plot).openMenu();
            });
            this.addItem(plotHourClock.getItem(), 25);
            this.addItem(plotInteractionsChest.getItem(), 26);

            int headIndex = (4 * 9);
            for(UUID playerID : plot.getMembers()) {
                ItemBuilder membersHead = new ItemBuilder(Material.PLAYER_HEAD);
                Player member = Bukkit.getPlayer(playerID);
                String memberName = member.getName();

                membersHead.setDisplayName(Component.text(memberName).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

                membersHead.setLore(Component.text("Rang: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                                .append(Component.text(plot.getMemberRole(playerID).getRoleName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA)));

                if(plot.isPlayerPresent(this.player.getUniqueId())) {
                    if (playerID == this.player.getUniqueId()) {
                        membersHead.setLore(Component.text("Rang: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                                        .append(Component.text(plot.getMemberRole(playerID).getRoleName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA))
                                , Component.text("")
                                , Component.text("Oh ! C'est toi !").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));

                    } else if(plot.getMemberRole(playerID) != PlotRoles.CHIEF) {
                        membersHead.setLore(Component.text("Rang: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                                        .append(Component.text(plot.getMemberRole(playerID).getRoleName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA))
                                , Component.text("")
                                , Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour modifier").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE))
                                , Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                    }
                }

                if((plot.getMemberRole(this.player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(this.player.getUniqueId()) == PlotRoles.DEPUTY) && playerID != this.player.getUniqueId() && plot.getMemberRole(playerID) != PlotRoles.CHIEF) {
                    this.addItem(membersHead.getItem(), headIndex, () -> new PlayerRoleMenu(this.player, member, plot).openMenu());
                } else {
                    this.addItem(membersHead.getItem(), headIndex);
                }

                headIndex++;
            }

            //Add buttons to add members (FOR CHIEF OR DEPUTY ONLY)
            if(plot.getMemberRole(this.player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(this.player.getUniqueId()) == PlotRoles.DEPUTY){

                ItemBuilder bannerAddMember = new ItemBuilder(Material.GREEN_BANNER);
                bannerAddMember.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS),
                        new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM),
                        new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP),
                        new Pattern(DyeColor.GREEN, PatternType.BORDER)).applyPatterns();
                bannerAddMember.setDisplayName(Component.text("[+] ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                        .append(Component.text("Inviter un joueur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD)));
                bannerAddMember.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                        .append(Component.text("Clic gauche pour inviter un joueur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),
                        Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                bannerAddMember.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

                this.addItem(bannerAddMember.getItem(), headIndex, () -> {
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                            .append(Component.text("Pour inviter un joueur dans ta zone, fais: \n").color(NamedTextColor.WHITE))
                            .append(Component.text("/fc invite <Pseudo>").color(NamedTextColor.AQUA).clickEvent(ClickEvent.suggestCommand("/fc invite "))));
                    player.getInventory().close();
                });
            }


        }
//        ItemBuilder playerHead = new ItemBuilder(Material.PLAYER_HEAD);
//        playerHead.setDisplayName(Component.text(player.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
//        playerHead.setLore(Component.text("Rang: X").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
//                Component.text("Gloires: X").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
//                Component.text("Faveurs: X").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
//                Component.text(""),
//                Component.text(String.format("Zones (total) : %d/%d", GamePlayer.getPlayer(player.getUniqueId()).getPlots().size(), 999)).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
//                Component.text(String.format("Zones (chef) : %d/%d", GamePlayer.getPlayer(player.getUniqueId()).getChefPlotsCount(), 999)).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
//
//        ItemBuilder options = new ItemBuilder(Material.COMPARATOR);
//        options.setDisplayName(Component.text("Options").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
//
//        ItemBuilder particles = new ItemBuilder(Material.REDSTONE);
//        particles.setDisplayName(Component.text("Particules").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
//
//        ItemBuilder shop = new ItemBuilder(Material.GOLD_INGOT);
//        shop.setDisplayName(Component.text("Boutique").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));



        ItemBuilder white_delimitation = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE);
        white_delimitation.setDisplayName(Component.text(" ").decoration(TextDecoration.ITALIC, false));
        ItemBuilder black_delimitation = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
        black_delimitation.setDisplayName(Component.text(" ").decoration(TextDecoration.ITALIC, false));

        //Set buttons (items) in inventory
        this.addItem(spawn.getItem(), 0);
        this.addItem(myPlots.getItem(), 1, () -> new PlotMenu(player).openMenu());
        this.addItem(freeZone.getItem(), 2);
//        this.addItem(playerHead.getItem(), 4);
//        this.addItem(options.getItem(), 6);
//        this.addItem(particles.getItem(), 7);
//        this.addItem(shop.getItem(), 8);

        for(int i = 0; i < 9; i++){
            this.addItem(white_delimitation.getItem(), i + 9);
            this.addItem(black_delimitation.getItem(), i + 27);
        }

    }


}
