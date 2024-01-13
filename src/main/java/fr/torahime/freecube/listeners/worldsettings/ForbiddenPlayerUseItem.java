package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.Material;
import org.bukkit.block.Jukebox;

public enum ForbiddenPlayerUseItem {

    GOAT_HORN(Material.GOAT_HORN, false),
    FIREWORK_ROCKET(Material.FIREWORK_ROCKET, true),
    JUKEBOX(Material.JUKEBOX, false),
    SPLASH_POTION(Material.SPLASH_POTION, true);

    private Material material;
    private boolean canBeUsedInADispenser;

    ForbiddenPlayerUseItem(Material material, boolean canBeUsedInADispenser) {
        this.material = material;
        this.canBeUsedInADispenser = canBeUsedInADispenser;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean canBeUsedInADispenser() {
        return canBeUsedInADispenser;
    }

}
