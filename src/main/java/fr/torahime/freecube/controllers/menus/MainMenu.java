package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.commands.players.plots.FindCommand;
import fr.torahime.freecube.controllers.custom_events.PlotEnterEvent;
import fr.torahime.freecube.controllers.menus.plots.PlayerRoleMenu;
import fr.torahime.freecube.controllers.menus.plots.MyPlotsMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.entity.EntityMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.hoursweather.HoursWeatherMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.interaction.InteractionsMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.music.MusicMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.preference.PreferencesMenu;
import fr.torahime.freecube.controllers.menus.plots.settings.pvp.PvpMenu;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.models.plots.PlotStates;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.UUID;

public class MainMenu extends Menu {

    public MainMenu(Player player){
        super(player, Component.text(player.getName() + " - " + (PlotIdentifier.isInPlot(player.getLocation()) ? PlotIdentifier.getPlotIndex(player.getLocation()) == 0 ? "Spawn" : String.format("Zone %d", PlotIdentifier.getPlotIndex(player.getLocation())) : "Route")), 54);
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

        PlotStates playerGameModeState = PlotStates.getFromGameMode(this.player.getGameMode());
        ItemBuilder changeGamemode = new ItemBuilder(Material.ENDER_EYE);
        changeGamemode.setDisplayName(Component.text("Changer de mode de jeu").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        changeGamemode.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour passer en ").color(NamedTextColor.WHITE).append(Component.text(PlotStates.getInverseState(playerGameModeState).getState()).color(NamedTextColor.YELLOW))).decoration(TextDecoration.ITALIC, false));

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
            plotPreferenceCraftTable.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("les préférences de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotHourClock = new ItemBuilder(Material.CLOCK);
            plotHourClock.setDisplayName(Component.text("Heure/Météo de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotHourClock.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text(" l'heure et la météo de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotInteractionsChest = new ItemBuilder(Material.CHEST);
            plotInteractionsChest.setDisplayName(Component.text("Interactions de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotInteractionsChest.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("les interactions de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotMusicsJukeBox = new ItemBuilder(Material.JUKEBOX);
            plotMusicsJukeBox.setDisplayName(Component.text("Émetteurs de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotMusicsJukeBox.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("les émetteurs de musique de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotEntityEgg = new ItemBuilder(Material.ALLAY_SPAWN_EGG);
            plotEntityEgg.setDisplayName(Component.text("Entitées de la zone").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotEntityEgg.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("les générateurs à entitées de la zone").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotPvpSword = new ItemBuilder(Material.DIAMOND_SWORD);
            plotPvpSword.setDisplayName(Component.text("Zone pvp").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotPvpSword.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour " + (plot.isPlayerPresent(this.player.getUniqueId()) ? "modifier" : "voir")).color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false), Component.text("les zones pvp actives").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

            ItemBuilder plotMoreCmdsBook = new ItemBuilder(Material.BOOK);
            plotMoreCmdsBook.setDisplayName(Component.text("Commandes supplémentaires").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            plotMoreCmdsBook.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Éjecter un invité de la zone:").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false),
                    Component.text("/fc kick <Pseudo>").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Bannir un vité de la zone:").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false),
                    Component.text("/fc ban <Pseudo>").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Quitter la zone: ").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false).append(Component.text("/out").color(NamedTextColor.AQUA)).decoration(TextDecoration.ITALIC, false));

            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY) {

                plotSpawnSunflower.addLore(Component.empty(), Component.text("Commande pour changer le spawn:").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                        Component.text("/fc setspawn").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                        Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));

                plotPreferenceCraftTable.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotHourClock.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotInteractionsChest.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotMusicsJukeBox.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotEntityEgg.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                plotPvpSword.addLore(Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
                this.addItem(changeGamemode.getItem(), 8, () -> {
                    player.setGameMode(PlotStates.getInverseState(playerGameModeState).getGameMode());
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                            .append(Component.text("Tu es maintenant en mode de jeu ").color(NamedTextColor.WHITE)
                                    .append(Component.text(PlotStates.getFromGameMode(player.getGameMode()).getState()).color(NamedTextColor.YELLOW))));
                    this.fillInventory();
                });
            }

            this.addItem(plotInfoGrassBlock.getItem(), 18);
            this.addItem(plotSpawnSunflower.getItem(), 19, () -> {
                this.player.teleport(plot.getSpawn());
                this.player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD)
                        .append(Component.text("Tu as été téléporté au spawn de la zone").color(NamedTextColor.WHITE)));
            });
            this.addItem(plotPreferenceCraftTable.getItem(), 20, () -> {
                new PreferencesMenu(this.player, plot, this).openMenu();
            });
            this.addItem(plotHourClock.getItem(), 21, () -> new HoursWeatherMenu(player, plot, this).openMenu());
            this.addItem(plotInteractionsChest.getItem(), 22, () -> new InteractionsMenu(player, plot, this).openMenu());
            this.addItem(plotMusicsJukeBox.getItem(), 23, () -> new MusicMenu(player, plot, this).openMenu());
            this.addItem(plotEntityEgg.getItem(), 24, () -> new EntityMenu(player, plot, this).openMenu());
            this.addItem(plotPvpSword.getItem(), 25, () -> new PvpMenu(player, plot, this).openMenu());
            this.addItem(plotMoreCmdsBook.getItem(), 26);

//            PLAYER HEAD
            int headIndex = (4 * 9);
            for(UUID playerID : plot.getMembers()) {
                ItemBuilder membersHead = new ItemBuilder(Material.PLAYER_HEAD);
                OfflinePlayer member = Bukkit.getOfflinePlayer(playerID);
                membersHead.setOwnerOfHead(member.getName());
                String memberName = member.getName();

                membersHead.setDisplayName(Component.text(memberName).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

                membersHead.setLore(Component.text("Rang: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                                .append(Component.text(plot.getMemberRole(playerID).getRoleName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA)));

                if(plot.isPlayerPresent(this.player.getUniqueId())) {


                    if (playerID.equals(this.player.getUniqueId())) {
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
                    this.addItem(membersHead.getItem(), headIndex, () -> new PlayerRoleMenu(this.player, member, plot, this).openMenu());
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
        this.addItem(spawn.getItem(), 0, () -> {
            this.player.teleport(PlotIdentifier.getPlotCenterLocation(0));
            Bukkit.getServer().getPluginManager().callEvent(new PlotEnterEvent(this.player));
        });
        this.addItem(myPlots.getItem(), 1, () -> new MyPlotsMenu(player).openMenu());
        this.addItem(freeZone.getItem(), 2, () -> FindCommand.findPlot(player));
//        this.addItem(playerHead.getItem(), 4);
//        this.addItem(options.getItem(), 6);
//        this.addItem(particles.getItem(), 7);
//        this.addItem(shop.getItem(), 8);

        for(int i = 0; i < 9; i++){
            this.addItem(white_delimitation.getItem(), i + 9);
            if(PlotIdentifier.isPlotClaimed(this.player.getLocation())) this.addItem(black_delimitation.getItem(), i + 27);

        }

    }


}
