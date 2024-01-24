package fr.torahime.freecube.models.entitys;

import org.bukkit.Material;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;

public enum PlotEntity {

    //Put all lviing entity here (except player), only peaceful entity are allowed, with their name, their corresponding spawn egg material, and their corresponding entity class
    //Example: COW("Vache", Material.COW_SPAWN_EGG, EntityType.COW)

    AXOLOTL("Axolotl", Material.AXOLOTL_SPAWN_EGG, EntityType.AXOLOTL),
    BAT("Chauve-souris", Material.BAT_SPAWN_EGG, EntityType.BAT),
    BEE("Abeille", Material.BEE_SPAWN_EGG, EntityType.BEE),
    CAT("Chat", Material.CAT_SPAWN_EGG, EntityType.CAT),
    CHICKEN("Poulet", Material.CHICKEN_SPAWN_EGG, EntityType.CHICKEN),
    COD("Morue", Material.COD_SPAWN_EGG, EntityType.COD),
    COW("Vache", Material.COW_SPAWN_EGG, EntityType.COW),
    DONKEY("Âne", Material.DONKEY_SPAWN_EGG, EntityType.DONKEY),
    FOX("Renard", Material.FOX_SPAWN_EGG, EntityType.FOX),
    GLOW_SQUID("Calamar luisant", Material.GLOW_SQUID_SPAWN_EGG, EntityType.GLOW_SQUID),
    GOAT("Chèvre", Material.GOAT_SPAWN_EGG, EntityType.GOAT),
    HORSE("Cheval", Material.HORSE_SPAWN_EGG, EntityType.HORSE),
    MUSHROOM_COW("Vache champignon", Material.MOOSHROOM_SPAWN_EGG, EntityType.MUSHROOM_COW),
    MULE("Mule", Material.MULE_SPAWN_EGG, EntityType.MULE),
    OCELOT("Ocelot", Material.OCELOT_SPAWN_EGG, EntityType.OCELOT),
    PANDA("Panda", Material.PANDA_SPAWN_EGG, EntityType.PANDA),
    PARROT("Perroquet", Material.PARROT_SPAWN_EGG, EntityType.PARROT),
    PIG("Cochon", Material.PIG_SPAWN_EGG, EntityType.PIG),
    PIGLIN("Piglin", Material.PIGLIN_SPAWN_EGG, EntityType.PIGLIN),
    PIGLIN_BRUTE("Piglin brute", Material.PIGLIN_BRUTE_SPAWN_EGG, EntityType.PIGLIN_BRUTE),
    PILLAGER("Pillager", Material.PILLAGER_SPAWN_EGG, EntityType.PILLAGER),
    POLAR_BEAR("Ours polaire", Material.POLAR_BEAR_SPAWN_EGG, EntityType.POLAR_BEAR),
    PUFFERFISH("Poisson-globe", Material.PUFFERFISH_SPAWN_EGG, EntityType.PUFFERFISH),
    RABBIT("Lapin", Material.RABBIT_SPAWN_EGG, EntityType.RABBIT),
    SALMON("Saumon", Material.SALMON_SPAWN_EGG, EntityType.SALMON),
    SHEEP("Mouton", Material.SHEEP_SPAWN_EGG, EntityType.SHEEP);

    public String name;
    public Material spawnEggMaterial;
    public EntityType entityType;

    PlotEntity(String name, Material spawnEggMaterial, EntityType entityType) {
        this.name = name;
        this.spawnEggMaterial = spawnEggMaterial;
        this.entityType = entityType;
    }

    public String getName() {
        return name;
    }

    public Material getSpawnEggMaterial() {
        return spawnEggMaterial;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static PlotEntity getPlotEntityFromName(String name) {
        for (PlotEntity pe : PlotEntity.values()) {
            if (pe.name.equalsIgnoreCase(name)) {
                return pe;
            }
        }
        return null;
    }



}






