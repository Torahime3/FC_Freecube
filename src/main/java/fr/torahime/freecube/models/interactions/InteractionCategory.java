package fr.torahime.freecube.models.interactions;

public enum InteractionCategory {
    REDSTONE,
    PRIMARY,
    DECORATION;

    public static InteractionCategory nextCategory(InteractionCategory category){
        return switch (category) {
            case REDSTONE -> DECORATION;
            case PRIMARY -> REDSTONE;
            case DECORATION -> PRIMARY;
        };
    }

}
