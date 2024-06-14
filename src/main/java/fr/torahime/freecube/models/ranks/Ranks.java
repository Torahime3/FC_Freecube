package fr.torahime.freecube.models.ranks;

import net.kyori.adventure.text.format.NamedTextColor;

public enum Ranks {

    PLAYER("Joueur", NamedTextColor.GRAY, new String[]{"freecube.player"}),
    VIP("VIP", NamedTextColor.YELLOW, new String[]{"freecube.vip"}),
    VIP_PLUS("VIP+", NamedTextColor.AQUA, new String[]{"freecube.vipplus"}),
    HERO("Héro", NamedTextColor.GREEN, new String[]{"freecube.hero"}),
    PERSO("GP", NamedTextColor.WHITE, new String[]{"freecube.perso"});

    private final String prefix;
    private final NamedTextColor color;
    private final String[] permissions;

    Ranks(String prefix, NamedTextColor color, String[] permissions) {
        this.prefix = prefix;
        this.color = color;
        this.permissions = permissions;
    }

    public String getPrefix() {
        return prefix;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
