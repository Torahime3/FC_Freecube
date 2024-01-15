package fr.torahime.freecube.models.weather;

import org.bukkit.Material;

public enum Weather {

    CLEAR("Ensoleillé", Material.SUNFLOWER),
    DOWNFALL("Pluvieux", Material.HEART_OF_THE_SEA);

    private final Material material;
    private final String name;

    Weather(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

}
