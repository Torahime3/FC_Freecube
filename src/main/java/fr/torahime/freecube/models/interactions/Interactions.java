package fr.torahime.freecube.models.interactions;

import org.bukkit.Material;

public enum Interactions {

    DISPENSER(Material.DISPENSER),
    DROPPER(Material.DROPPER),
    TNT(Material.TNT),
    CHEST(Material.CHEST),
    CRAFT_TABLE(Material.CRAFTING_TABLE),
    FURNACE(Material.FURNACE),
    DOOR(Material.OAK_DOOR),
    BUTTON(Material.STONE_BUTTON),
    OAK_BUTTON(Material.OAK_BUTTON),
    LEVER(Material.LEVER),
    TRAPDOOR(Material.OAK_TRAPDOOR),
    ENCHANTING_TABLE(Material.ENCHANTING_TABLE),
    BREWING_STAND(Material.BREWING_STAND),
    ENDER_PORTAL_FRAME(Material.END_PORTAL_FRAME),
    DRAGON_EGG(Material.DRAGON_EGG),
    ENDER_CHEST(Material.ENDER_CHEST),
    BEACON(Material.BEACON),
    ANVIL(Material.ANVIL),
    TRAPPED_CHEST(Material.TRAPPED_CHEST),
    HOPPER(Material.HOPPER),
    IRON_TRAPDOOR(Material.IRON_TRAPDOOR),

    //FENCE GATE
    FENCE_GATE(Material.OAK_FENCE_GATE),
    ACACIA_FENCE_GATE(Material.ACACIA_FENCE_GATE),
    BIRCH_FENCE_GATE(Material.BIRCH_FENCE_GATE),
    DARK_OAK_FENCE_GATE(Material.DARK_OAK_FENCE_GATE),
    JUNGLE_FENCE_GATE(Material.JUNGLE_FENCE_GATE),
    SPRUCE_FENCE_GATE(Material.SPRUCE_FENCE_GATE),
    MANGROVE_FENCE_GATE(Material.MANGROVE_FENCE_GATE),
    CHERRY_FENCE_GATE(Material.CHERRY_FENCE_GATE),
    BAMBOO_FENCE_GATE(Material.BAMBOO_FENCE_GATE),
    CRIMSON_FENCE_GATE(Material.CRIMSON_FENCE_GATE),
    WARPED_FENCE_GATE(Material.WARPED_FENCE_GATE),

    IRON_DOOR(Material.IRON_DOOR),
    OAK_DOOR(Material.OAK_DOOR),
    ACACIA_DOOR(Material.ACACIA_DOOR),
    BIRCH_DOOR(Material.BIRCH_DOOR),
    DARK_OAK_DOOR(Material.DARK_OAK_DOOR),
    JUNGLE_DOOR(Material.JUNGLE_DOOR),
    SPRUCE_DOOR(Material.SPRUCE_DOOR);

    private final Material material;

    Interactions(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

}
