package fr.torahime.freecube.models.plots;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;

public enum PlotStates {

    LOW("Faible", NamedTextColor.RED, null),
    MEDIUM("Moyen", NamedTextColor.YELLOW, null),
    HIGH("Élevée", NamedTextColor.GREEN, null),
    ACTIVATE("Activer", NamedTextColor.GREEN, false),
    DEACTIVATE("Désactiver", NamedTextColor.RED, true),
    SURVIVAL("Survie", NamedTextColor.WHITE, null, GameMode.SURVIVAL),
    CREATIVE("Créatif", NamedTextColor.WHITE, null, GameMode.CREATIVE),
    ADVENTURE("Aventure", NamedTextColor.WHITE, null, GameMode.ADVENTURE);

    private final String state;
    private final NamedTextColor color;
    private final Boolean cancelEvent;
    private final GameMode gameMode;

    PlotStates(String state, NamedTextColor color, Boolean cancelEvent, GameMode gameMode) {
        this.cancelEvent = cancelEvent;
        this.state = state;
        this.color = color;
        this.gameMode = gameMode;
    }

    PlotStates(String state, NamedTextColor color, Boolean cancelEvent) {
        this.cancelEvent = cancelEvent;
        this.state = state;
        this.color = color;
        this.gameMode = null;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Boolean getCancelEvent() {
        return cancelEvent;
    }

    public String getState() {
        return state;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public static PlotStates fromLiteralString(String literal){
        return switch (literal) {
            case "true" -> ACTIVATE;
            case "false" -> DEACTIVATE;
            case "creative" -> CREATIVE;
            case "adventure" -> ADVENTURE;
            case "survival" -> SURVIVAL;
            default -> LOW;
        };
    }

    public static String mapStateToLiteralString(PlotStates states){
        return switch (states) {
            case ACTIVATE -> "true";
            case CREATIVE -> "creative";
            case ADVENTURE -> "adventure";
            case SURVIVAL -> "survival";
            default -> "false";
        };
    }

    public static PlotStates getInverseState(PlotStates state){

        return switch(state){
            case ACTIVATE -> DEACTIVATE;
            case DEACTIVATE -> ACTIVATE;
            case SURVIVAL -> CREATIVE;
            case CREATIVE -> ADVENTURE;
            case ADVENTURE -> SURVIVAL;
            case LOW -> MEDIUM;
            case MEDIUM -> HIGH;
            case HIGH -> LOW;
        };

    }

    public static String getInverseStateLiteral(PlotStates state){
        return getInverseState(state).getState();
    }

}
