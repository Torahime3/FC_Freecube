package fr.torahime.freecube.models.musics;

import org.bukkit.Material;
import org.bukkit.Sound;

public enum Music {

    NONE("Pas de musique", "None", null, Material.BARRIER),
    CAT("Cat", "C418", Sound.MUSIC_DISC_CAT, Material.MUSIC_DISC_CAT),
    BLOCKS("Blocks", "C418", Sound.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_BLOCKS),
    CHIRP("Chirp", "C418", Sound.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_CHIRP),
    FAR("Far", "C418", Sound.MUSIC_DISC_FAR, Material.MUSIC_DISC_FAR),
    MALL("Mall", "C418", Sound.MUSIC_DISC_MALL, Material.MUSIC_DISC_MALL),
    MELLOHI("Mellohi", "C418", Sound.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_MELLOHI),
    STAL("Stal", "C418", Sound.MUSIC_DISC_STAL, Material.MUSIC_DISC_STAL),
    STRAD("Strad", "C418", Sound.MUSIC_DISC_STRAD, Material.MUSIC_DISC_STRAD),
    WAIT("Wait", "C418", Sound.MUSIC_DISC_WAIT, Material.MUSIC_DISC_WAIT),
    WARD("Ward", "C418", Sound.MUSIC_DISC_WARD, Material.MUSIC_DISC_WARD),
    OTHERSIDE("Otherside", "Lena Raine", Sound.MUSIC_DISC_OTHERSIDE, Material.MUSIC_DISC_OTHERSIDE),
    RELICS("Relic", "Aaron Cherof", Sound.MUSIC_DISC_RELIC, Material.MUSIC_DISC_RELIC),
    PIGSTEP("Pigstep", "Lena Raine", Sound.MUSIC_DISC_PIGSTEP, Material.MUSIC_DISC_PIGSTEP);

    private final String name;
    private final String author;
    private final Sound sound;
    private final Material material;

    Music(String name, String author, Sound sound, Material material) {
        this.name = name;
        this.author = author;
        this.sound = sound;
        this.material = material;
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
}
