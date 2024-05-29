package fr.torahime.freecube.models.preferences;

import org.bukkit.Material;

public enum Preference {

    FLY("fly", "Voler", new String[]{"Les joueurs peuvent-ils voler dans la","zone ? (Cela n'affecte pas les","associés/membres)"}, Material.FEATHER),
    GAMEMODE("gamemode", "Mode de jeu", new String[]{"Le mode de jeu des joueurs qui entrent","dans la zone ? (Cela n'affecte pas les","associés/membres)"}, Material.DIAMOND_PICKAXE),
    CLEARINVENTORY("clearInventory","Vider l'inventaire", new String[]{"Vider l'inventaire des joueurs qui","entrent dans la zone ? (Cela n'affecte","pas les associés/membres)"}, Material.LAVA_BUCKET),
    SPAWNTP("spawnTp","Rejoindre au spawn", new String[]{"Les joueurs qui entrent dans la zone","sont forcément téléportés au spawn de","la zone ? (Cela n'affecte pas les","associés/membres)"}, Material.SUNFLOWER),
    DROPITEMS("dropItems","Jeter des objets", new String[]{"Les joueurs peuvent-ils jeter des","objets dans la zone ? (Cela n'affecte","pas les associés/membres)"}, Material.CRAFTING_TABLE),
    LOOTITEMS("lootItems","Ramasser des objets",new String[]{"Les joueurs peuvent-ils ramasser des","objets dans la zone ? (Cela n'affecte","pas les associés/membres)"}, Material.FISHING_ROD);


    private final String code;
    private final String title;
    private final String[] lore;
    private final Material material;

    Preference(String code, String title, String[] lore, Material material) {
        this.code = code;
        this.title = title;
        this.lore = lore;
        this.material = material;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String[] getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public static Preference fromCode(String code){
        for(Preference preference : values()){
            if(preference.getCode().equals(code)){
                return preference;
            }
        }
        return null;
    }

}
