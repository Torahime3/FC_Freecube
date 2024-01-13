package fr.torahime.freecube.controllers.menus.plots.settings.interactions;

public enum Category {
    REDSTONE,
    PRIMARY,
    DECORATION;

    public static Category nextCategory(Category category){
        return switch (category) {
            case REDSTONE -> DECORATION;
            case PRIMARY -> REDSTONE;
            case DECORATION -> PRIMARY;
        };
    }

}
