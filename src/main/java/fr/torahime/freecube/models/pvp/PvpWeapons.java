package fr.torahime.freecube.models.pvp;

import org.bukkit.Material;

public enum PvpWeapons {

    SWORDS("SWORD", "Dégâts à l'épée", Material.DIAMOND_SWORD),
    SHOVELS("SHOVEL", "Dégâts à la pelle", Material.DIAMOND_SHOVEL),
    PICKAXES("PICKAXE", "Dégâts à la pioche", Material.DIAMOND_PICKAXE),
    AXES("AXE", "Dégâts à la hache", Material.DIAMOND_AXE),
    AIR("AIR", "Dégâts à mains nues", Material.PLAYER_HEAD),
    CROSSBOWS("CROSSBOW", "Dégâts à l'arbalète", Material.CROSSBOW),
    BOWS("BOW","Dégâts à l'arc", Material.BOW),
    TRIDENTS("TRIDENT" , "Dégâts au trident", Material.TRIDENT);

    private final String code;
    private final String name;
    private final Material material;

    PvpWeapons(String code, String name, Material material) {
        this.code = code;
        this.name = name;
        this.material = material;
    }

    public String getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public static PvpWeapons getByName(String name) {
        for (PvpWeapons pvpWeapons : values()) {
            if (name.toLowerCase().toLowerCase().contains(pvpWeapons.getCode().toLowerCase())) {
                System.out.println("Found weapon: " + pvpWeapons.getName() + " with code: " + pvpWeapons.getCode() + " and name: " + name);
                return pvpWeapons;
            }
        }
        return null;
    }
}
