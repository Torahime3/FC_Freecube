package fr.torahime.freecube.controllers.menus.plots.settings.music;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.models.musics.Music;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.models.plots.PlotRoles;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class MusicMenu extends Menu {

    Plot plot;

    public MusicMenu(Player player, Plot plot, Menu lastMenu){
        super(player, Component.text("Zone " + plot.getId() + " > Émetteurs de musique"), 54, lastMenu);
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){
            ItemBuilder bannerAddMusic = new ItemBuilder(Material.GREEN_BANNER);
            bannerAddMusic.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM),
                    new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP),
                    new Pattern(DyeColor.GREEN, PatternType.BORDER)).applyPatterns();
            bannerAddMusic.setDisplayName(Component.text("[+] ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                    .append(Component.text("Créer un nouvel émetteur de musique").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD)));
            bannerAddMusic.setLore(Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)
                            .append(Component.text("Clic gauche pour créer un émetteur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),Component.text("à la position où vous êtes.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE),
                    Component.text("(accessible au rang Adjoint)").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.GRAY));
            bannerAddMusic.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            this.addItem(bannerAddMusic.getItem(), 0, () -> {
                if(plot.getMusicTransmitterByLocation(player.getLocation()) != null){
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Un émetteur de musique existe déjà à cet endroit.").color(NamedTextColor.RED)));
                    return;
                }

                if(PlotIdentifier.getPlotIndex(player.getLocation()) != plot.getId()){
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous devez être dans la zone pour créer un émetteur de musique.").color(NamedTextColor.RED)));
                    return;
                }

                if(plot.getMusicTransmitters().size() >= plot.getMAX_MUSIC_TRANSMITTERS()){
                    player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez atteint le nombre maximum d'émetteurs de musique.").color(NamedTextColor.RED)));
                    return;
                }

                MusicTransmitter mt = new MusicTransmitter(Music.CAT, player.getLocation(), 1, 1);
                plot.addMusicTransmitter(mt);
                new MusicConfMenu(player, plot, mt, this).openMenu();
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("L'émetteur de musique a été créé.").color(NamedTextColor.WHITE)));

            });
        }


        int index = plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY ? 1 : 0;
        for(MusicTransmitter mt : plot.getMusicTransmitters()){

            ItemBuilder musicItem = new ItemBuilder(mt.getMusic().getMaterial());
            musicItem.setDisplayName(Component.text(String.format("X:%s Y:%s Z:%s",Math.round(mt.getLocation().getX()), Math.round(mt.getLocation().getY()), Math.round(mt.getLocation().getZ()))).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            musicItem.setLore(
                    Component.text("Son:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(" " + mt.getMusic().getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)),
                    Component.text("Volume:").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW).append(Component.text(" " + mt.getVolume() * 20 + "%").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));


            if(plot.getMemberRole(player.getUniqueId()) == PlotRoles.CHIEF || plot.getMemberRole(player.getUniqueId()) == PlotRoles.DEPUTY){

                musicItem.addLore(Component.empty(),
                        Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour configurer l'émetteur de musique.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));


                this.addItem(musicItem.getItem(), index, () -> {
                    new MusicConfMenu(player, plot, mt, this).openMenu();
                });
            } else {
                this.addItem(musicItem.getItem(), index);
            }

            index++;
        }

        super.fillInventory();

    }

}
