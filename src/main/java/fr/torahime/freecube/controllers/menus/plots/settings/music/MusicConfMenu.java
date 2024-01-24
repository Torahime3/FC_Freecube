package fr.torahime.freecube.controllers.menus.plots.settings.music;

import fr.torahime.freecube.controllers.menus.Menu;
import fr.torahime.freecube.models.Plot;
import fr.torahime.freecube.models.musics.Music;
import fr.torahime.freecube.models.musics.MusicTransmitter;
import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.awt.*;

public class MusicConfMenu extends Menu {

    MusicTransmitter mt;
    Plot plot;

    public MusicConfMenu(Player player, Plot plot, MusicTransmitter mt, Menu lastMenu) {
        super(player, Component.text("Zone " + plot.getId() + " > Musique > " + mt.getMusic().getName()), 54, lastMenu);
        this.mt = mt;
        this.plot = plot;
    }

    @Override
    public void fillInventory() {

        ItemBuilder deleteMusicTransmitter = new ItemBuilder(Material.BARRIER);
        deleteMusicTransmitter.setDisplayName(Component.text("Supprimer l'émetteur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
        deleteMusicTransmitter.setLore(Component.empty(),
                Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour supprimer l'émetteur de musique.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));;

        ItemBuilder musicTeleporter = new ItemBuilder(Material.COMPASS);
        musicTeleporter.setDisplayName(Component.text("Se téléporter à l'émetteur").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
        musicTeleporter.setLore(Component.empty(),
                Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour vous téléporter à l'émetteur.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

        this.addItem(musicTeleporter.getItem(), 17, () -> {
            player.teleport(mt.getLocation());
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Vous avez été téléporté à l'émetteur de musique.").color(NamedTextColor.WHITE)));
            player.getInventory().close();
        });

        this.addItem(deleteMusicTransmitter.getItem(), 0, () -> {
            plot.removeMusicTransmitter(mt);
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("L'émetteur de musique a été supprimé. Les changements prendront effet la prochaine fois que vous entrez dans la zone.").color(NamedTextColor.WHITE)));
            player.getInventory().close();
        });

        int index = 1;
        for(Music music : Music.values()) {

            ItemBuilder musicItem = new ItemBuilder(music.getMaterial());
            musicItem.setDisplayName(Component.text(music.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD));
            musicItem.setLore(Component.empty(),
                    Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour jouer la musique à cet émetteur.").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            if(mt.getMusic() == music){
                musicItem.setDisplayName(Component.text(music.getName()).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text(" (Actuel)").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)));
                musicItem.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                musicItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            this.addItem(musicItem.getItem(), index, () -> {
                mt.setMusic(music);
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Les changements prendront effet la prochaine fois que vous entrez dans la zone.").color(NamedTextColor.WHITE)));
                this.fillInventory();
            });
            index++;
        }

        for(int i = 1; i < 6; i++) {
            ItemBuilder redVolumeItem = new ItemBuilder(Material.RED_TERRACOTTA);
            redVolumeItem.setDisplayName(Component.text("Volume " + (20 * i) + "%").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.RED));
            redVolumeItem.setLore(Component.empty(),
                    Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour définir le volume").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            final int finalI = i;
            this.addItem(redVolumeItem.getItem(), 28 + i, () -> {
                mt.setVolume(finalI);
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Les changements prendront effet la prochaine fois que vous entrez dans la zone.").color(NamedTextColor.WHITE)));
                this.fillInventory();
            });
        }

        for (int i = 1; i <= mt.getVolume(); i++) {
            ItemBuilder greenVolumeItem = new ItemBuilder(Material.GREEN_TERRACOTTA);
            greenVolumeItem.setDisplayName(Component.text("Volume " + (20 * i) + "%").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN));
            greenVolumeItem.setLore(Component.empty(),
                    Component.text("> ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN).append(Component.text("Clic gauche pour définir le volume").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));

            final int finalI = i;
            this.addItem(greenVolumeItem.getItem(), 28 + i, () -> {
                mt.setVolume(finalI);
                player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Les changements prendront effet la prochaine fois que vous entrez dans la zone.").color(NamedTextColor.WHITE)));
                this.fillInventory();
            });
        }

        super.fillInventory();
    }

}
