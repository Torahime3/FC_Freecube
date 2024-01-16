package fr.torahime.freecube.models.musics;

import org.bukkit.Material;
import org.bukkit.Sound;

public enum Music {

    CAT("Cat", "C418", Sound.MUSIC_DISC_CAT, Material.MUSIC_DISC_CAT, 187),
    BLOCKS("Blocks", "C418", Sound.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_BLOCKS, 348),
    CHIRP("Chirp", "C418", Sound.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_CHIRP, 188),
    FAR("Far", "C418", Sound.MUSIC_DISC_FAR, Material.MUSIC_DISC_FAR,177),
    MALL("Mall", "C418", Sound.MUSIC_DISC_MALL, Material.MUSIC_DISC_MALL, 200),
    MELLOHI("Mellohi", "C418", Sound.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_MELLOHI, 100),
    STAL("Stal", "C418", Sound.MUSIC_DISC_STAL, Material.MUSIC_DISC_STAL, 153),
    STRAD("Strad", "C418", Sound.MUSIC_DISC_STRAD, Material.MUSIC_DISC_STRAD, 192),
    WAIT("Wait", "C418", Sound.MUSIC_DISC_WAIT, Material.MUSIC_DISC_WAIT, 241),
    WARD("Ward", "C418", Sound.MUSIC_DISC_WARD, Material.MUSIC_DISC_WARD, 254),
    OTHERSIDE("Otherside", "Lena Raine", Sound.MUSIC_DISC_OTHERSIDE, Material.MUSIC_DISC_OTHERSIDE, 197),
    RELIC("Relic", "Aaron Cherof", Sound.MUSIC_DISC_RELIC, Material.MUSIC_DISC_RELIC, 180),
    PIGSTEP("Pigstep", "Lena Raine", Sound.MUSIC_DISC_PIGSTEP, Material.MUSIC_DISC_PIGSTEP, 154);

    private final String name;
    private final String author;
    private final Sound sound;
    private final Material material;
    private final int duration;

    Music(String name, String author, Sound sound, Material material, int duration) {
        this.name = name;
        this.author = author;
        this.sound = sound;
        this.material = material;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Sound getSound() {
        return sound;
    }

    public Material getMaterial() {
        return material;
    }

    public int getDuration() {
        return duration;
    }
}
