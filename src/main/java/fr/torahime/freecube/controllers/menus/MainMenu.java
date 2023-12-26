package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.models.GamePlayer;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

public class MainMenu extends Menu {

    public MainMenu(Player player){
        super(player, Component.text(player.getName() + " - Zone " + PlotIdentifier.getPlotIndex(player.getLocation())), 54);
    }

    @Override
    public void fillInventory(){

        //Create buttons (items)
        ItemBuilder spawn = new ItemBuilder(Material.BLUE_BANNER);
        spawn.setDisplayName(Component.text("Aller au spawn du monde").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        spawn.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour aller au spawn du monde").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        ItemBuilder myPlots = new ItemBuilder(Material.OAK_SIGN);
        myPlots.setDisplayName(Component.text("Mes Zones").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));

        ItemBuilder freeZone = new ItemBuilder(Material.PAPER);
        freeZone.setDisplayName(Component.text("Trouver une zone libre").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        freeZone.setLore(Component.text("> ").color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour trouver une zone libre").color(NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));


        if(PlotIdentifier.isInPlot(this.player.getLocation()) && PlotIdentifier.isPlotClaimed(this.player.getLocation())) {

            Plot plot = Plot.getPlot(PlotIdentifier.getPlotIndex(player.getLocation()));

            int headIndex = (4 * 9);
            for(UUID playerID : plot.getMembers()) {
                ItemBuilder membersHead = new ItemBuilder(Material.PLAYER_HEAD);
                membersHead.setDisplayName(Component.text(Objects.requireNonNull(Bukkit.getPlayer(playerID)).getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
                membersHead.setLore(Component.text("Rang: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE).append(Component.text(plot.getMemberRole(player.getUniqueId()).getRoleName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA)));
                this.addItem(membersHead.getItem(), headIndex);
                headIndex++;
            }

            if(plot.getOwner() == this.player.getUniqueId()){

                ItemBuilder bannerAddMember = new ItemBuilder(Material.GREEN_BANNER);
                bannerAddMember.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS),
                        new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM),
                        new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP),
                        new Pattern(DyeColor.GREEN, PatternType.BORDER)).applyPatterns();
                bannerAddMember.setDisplayName(Component.text("[+] Ajouter un membre").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN));
                bannerAddMember.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

                ItemBuilder bannerRemoveMember = new ItemBuilder(Material.RED_BANNER);
                bannerRemoveMember.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
                        new Pattern(DyeColor.RED, PatternType.BORDER)).applyPatterns();
                bannerRemoveMember.setDisplayName(Component.text("[-] Supprimer un membre").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
                bannerRemoveMember.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

                this.addItem(bannerAddMember.getItem(), headIndex);
                this.addItem(bannerRemoveMember.getItem(), headIndex + 1);
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
        ItemBuilder black_delimitation = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
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
