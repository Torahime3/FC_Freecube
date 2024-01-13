package fr.torahime.freecube.models.interactions;

import fr.torahime.freecube.controllers.menus.plots.settings.interactions.Category;
import fr.torahime.freecube.models.PlotStates;
import org.bukkit.Material;

import java.util.ArrayList;

public enum Interactions {

    //BLOCKS INTERACTIONS

    /*

    PRIMARY

     */
    CHEST(Material.CHEST),
    CRAFT_TABLE(Material.CRAFTING_TABLE),
    FURNACE(Material.FURNACE),
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
    BELL(Material.BELL),
    SHULKER_BOX(Material.SHULKER_BOX),
    WHITE_SHULKER_BOX(Material.WHITE_SHULKER_BOX),
    ORANGE_SHULKER_BOX(Material.ORANGE_SHULKER_BOX),
    MAGENTA_SHULKER_BOX(Material.MAGENTA_SHULKER_BOX),
    LIGHT_BLUE_SHULKER_BOX(Material.LIGHT_BLUE_SHULKER_BOX),
    YELLOW_SHULKER_BOX(Material.YELLOW_SHULKER_BOX),
    LIME_SHULKER_BOX(Material.LIME_SHULKER_BOX),
    PINK_SHULKER_BOX(Material.PINK_SHULKER_BOX),
    GRAY_SHULKER_BOX(Material.GRAY_SHULKER_BOX),
    LIGHT_GRAY_SHULKER_BOX(Material.LIGHT_GRAY_SHULKER_BOX),
    CYAN_SHULKER_BOX(Material.CYAN_SHULKER_BOX),
    PURPLE_SHULKER_BOX(Material.PURPLE_SHULKER_BOX),
    BLUE_SHULKER_BOX(Material.BLUE_SHULKER_BOX),
    BROWN_SHULKER_BOX(Material.BROWN_SHULKER_BOX),
    GREEN_SHULKER_BOX(Material.GREEN_SHULKER_BOX),
    RED_SHULKER_BOX(Material.RED_SHULKER_BOX),
    BLACK_SHULKER_BOX(Material.BLACK_SHULKER_BOX),


    /*

    REDSTONE

     */

    DISPENSER(Category.REDSTONE, Material.DISPENSER),
    DROPPER(Category.REDSTONE, Material.DROPPER),
    TNT(Category.REDSTONE,Material.TNT, PlotStates.DEACTIVATE),
    LEVER(Category.REDSTONE, Material.LEVER),
    TRIPWIRES(Category.REDSTONE, Material.TRIPWIRE_HOOK),

    //REDSTONE EXCEPTED
    REDSTONE_COMPARATOR(Category.REDSTONE, Material.COMPARATOR, PlotStates.DEACTIVATE),
    REDSTONE_REPEATER(Category.REDSTONE, Material.REPEATER, PlotStates.DEACTIVATE),

    //BUTTONS
    BUTTON(Category.REDSTONE, Material.STONE_BUTTON),
    OAK_BUTTON(Category.REDSTONE, Material.OAK_BUTTON),
    ACACIA_BUTTON(Category.REDSTONE, Material.ACACIA_BUTTON),
    BIRCH_BUTTON(Category.REDSTONE, Material.BIRCH_BUTTON),
    DARK_OAK_BUTTON(Category.REDSTONE, Material.DARK_OAK_BUTTON),
    JUNGLE_BUTTON(Category.REDSTONE,Material.JUNGLE_BUTTON),
    SPRUCE_BUTTON(Category.REDSTONE,Material.SPRUCE_BUTTON),
    CRIMSON_BUTTON(Category.REDSTONE, Material.CRIMSON_BUTTON),
    WARPED_BUTTON(Category.REDSTONE, Material.WARPED_BUTTON),
    CHERRY_BUTTON(Category.REDSTONE,Material.CHERRY_BUTTON),
    BAMBOO_BUTTON(Category.REDSTONE, Material.BAMBOO_BUTTON),
    MANGROVE_BUTTON(Category.REDSTONE, Material.MANGROVE_BUTTON),
    POLISHED_BLACKSTONE_BUTTON(Category.REDSTONE, Material.POLISHED_BLACKSTONE_BUTTON),

    //PRESSURE PLATE
    OAK_PRESSURE_PLATE(Category.REDSTONE, Material.OAK_PRESSURE_PLATE),
    STONE_PRESSURE_PLATE(Category.REDSTONE, Material.STONE_PRESSURE_PLATE),
    LIGHT_WEIGHTED_PRESSURE_PLATE(Category.REDSTONE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE),
    HEAVY_WEIGHTED_PRESSURE_PLATE(Category.REDSTONE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE),
    ACACIA_PRESSURE_PLATE(Category.REDSTONE, Material.ACACIA_PRESSURE_PLATE),
    BIRCH_PRESSURE_PLATE(Category.REDSTONE, Material.BIRCH_PRESSURE_PLATE),
    DARK_OAK_PRESSURE_PLATE(Category.REDSTONE, Material.DARK_OAK_PRESSURE_PLATE),
    JUNGLE_PRESSURE_PLATE(Category.REDSTONE, Material.JUNGLE_PRESSURE_PLATE),
    SPRUCE_PRESSURE_PLATE(Category.REDSTONE, Material.SPRUCE_PRESSURE_PLATE),
    CRIMSON_PRESSURE_PLATE(Category.REDSTONE, Material.CRIMSON_PRESSURE_PLATE),
    WARPED_PRESSURE_PLATE(Category.REDSTONE, Material.WARPED_PRESSURE_PLATE),
    CHERRY_PRESSURE_PLATE(Category.REDSTONE, Material.CHERRY_PRESSURE_PLATE),
    BAMBOO_PRESSURE_PLATE(Category.REDSTONE, Material.BAMBOO_PRESSURE_PLATE),
    MANGROVE_PRESSURE_PLATE(Category.REDSTONE, Material.MANGROVE_PRESSURE_PLATE),
    POLISHED_BLACKSTONE_PRESSURE_PLATE(Category.REDSTONE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE),

    //TRAP DOORS
    OAK_TRAPDOOR(Category.DECORATION, Material.OAK_TRAPDOOR),
    ACACIA_TRAPDOOR(Category.DECORATION, Material.ACACIA_TRAPDOOR),
    BIRCH_TRAPDOOR(Category.DECORATION, Material.BIRCH_TRAPDOOR),
    DARK_OAK_TRAPDOOR(Category.DECORATION, Material.DARK_OAK_TRAPDOOR),
    JUNGLE_TRAPDOOR(Category.DECORATION, Material.JUNGLE_TRAPDOOR),
    SPRUCE_TRAPDOOR(Category.DECORATION, Material.SPRUCE_TRAPDOOR),
    CRIMSON_TRAPDOOR(Category.DECORATION, Material.CRIMSON_TRAPDOOR),
    WARPED_TRAPDOOR(Category.DECORATION, Material.WARPED_TRAPDOOR),
    CHERRY_TRAPDOOR(Category.DECORATION, Material.CHERRY_TRAPDOOR),
    BAMBOO_TRAPDOOR(Category.DECORATION, Material.BAMBOO_TRAPDOOR),
    MANGROVE_TRAPDOOR(Category.DECORATION, Material.MANGROVE_TRAPDOOR),



    /*

    DECORATION

     */

    //FENCE GATE
    FENCE_GATE(Category.DECORATION, Material.OAK_FENCE_GATE),
    ACACIA_FENCE_GATE(Category.DECORATION, Material.ACACIA_FENCE_GATE),
    BIRCH_FENCE_GATE(Category.DECORATION, Material.BIRCH_FENCE_GATE),
    DARK_OAK_FENCE_GATE(Category.DECORATION, Material.DARK_OAK_FENCE_GATE),
    JUNGLE_FENCE_GATE(Category.DECORATION, Material.JUNGLE_FENCE_GATE),
    SPRUCE_FENCE_GATE(Category.DECORATION, Material.SPRUCE_FENCE_GATE),
    MANGROVE_FENCE_GATE(Category.DECORATION, Material.MANGROVE_FENCE_GATE),
    CHERRY_FENCE_GATE(Category.DECORATION, Material.CHERRY_FENCE_GATE),
    BAMBOO_FENCE_GATE(Category.DECORATION, Material.BAMBOO_FENCE_GATE),
    CRIMSON_FENCE_GATE(Category.DECORATION, Material.CRIMSON_FENCE_GATE),
    WARPED_FENCE_GATE(Category.DECORATION, Material.WARPED_FENCE_GATE),

    //DOORS
    DOOR(Category.DECORATION, Material.OAK_DOOR),
    IRON_DOOR(Category.DECORATION, Material.IRON_DOOR),
    OAK_DOOR(Category.DECORATION, Material.OAK_DOOR),
    ACACIA_DOOR(Category.DECORATION, Material.ACACIA_DOOR),
    BIRCH_DOOR(Category.DECORATION, Material.BIRCH_DOOR),
    DARK_OAK_DOOR(Category.DECORATION, Material.DARK_OAK_DOOR),
    JUNGLE_DOOR(Category.DECORATION, Material.JUNGLE_DOOR),
    SPRUCE_DOOR(Category.DECORATION, Material.SPRUCE_DOOR),
    MANGROVE_DOOR(Category.DECORATION, Material.MANGROVE_DOOR),
    CHERRY_DOOR(Category.DECORATION, Material.CHERRY_DOOR),
    BAMBOO_DOOR(Category.DECORATION, Material.BAMBOO_DOOR),
    CRIMSON_DOOR(Category.DECORATION, Material.CRIMSON_DOOR),
    WARPED_DOOR(Category.DECORATION, Material.WARPED_DOOR);

    private final Material material;
    private PlotStates defaultPlotState;
    private Category category;

    Interactions(Material material) {
        this.material = material;
        this.category = Category.PRIMARY;
        this.defaultPlotState = PlotStates.ACTIVATE;
    }

    Interactions(Category category, Material material) {
        this.category = category;
        this.material = material;
        this.defaultPlotState = PlotStates.ACTIVATE;
    }

    Interactions(Category category, Material material, PlotStates defaultPlotState) {
        this.category = category;
        this.material = material;
        this.defaultPlotState = defaultPlotState;
    }

    public Material getMaterial() {
        return material;
    }

    public PlotStates getDefaultPlotState() {
        return defaultPlotState;
    }
    public Category getCategory() {
        return category;
    }

    public static ArrayList<Interactions> getAllInteractionsInCategory(Category category) {
        ArrayList<Interactions> interactions = new ArrayList<Interactions>();
        for (Interactions interaction : Interactions.values()) {
            if (interaction.getCategory() == category) {
                interactions.add(interaction);
            }
        }
        return interactions;
    }

}
