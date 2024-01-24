package fr.torahime.freecube.listeners.worldsettings;

import org.bukkit.Material;

public enum ForbiddenItem {

    GOAT_HORN(Material.GOAT_HORN, false),
    FIREWORK_ROCKET(Material.FIREWORK_ROCKET, true),
    JUKEBOX(Material.JUKEBOX, false),
    SPLASH_POTION(Material.SPLASH_POTION, true),
    MOB_SPAWNER(Material.SPAWNER, false),
    TNT_MINECART(Material.TNT_MINECART, false),
    END_CRYSTAL(Material.END_CRYSTAL, false),
    PUFFERFISH_BUCKET(Material.PUFFERFISH_BUCKET, false),
    SALMON_BUCKET(Material.SALMON_BUCKET, false),
    COD_BUCKET(Material.COD_BUCKET, false),
    TROPICAL_FISH_BUCKET(Material.TROPICAL_FISH_BUCKET, false),
    AXOLOTL_BUCKET(Material.AXOLOTL_BUCKET, false),
    BARRIER(Material.BARRIER, false);

    private Material material;
    private boolean canBeUsedInADispenser;

    ForbiddenItem(Material material, boolean canBeUsedInADispenser) {
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
