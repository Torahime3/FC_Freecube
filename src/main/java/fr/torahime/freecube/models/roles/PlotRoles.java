package fr.torahime.freecube.models.roles;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public enum PlotRoles {

    CHIEF("Chef", 0, NamedTextColor.RED, Material.RED_WOOL),
    DEPUTY("Adjoint", 1, NamedTextColor.GOLD, Material.ORANGE_WOOL),
    MEMBER("Membre", 2, NamedTextColor.YELLOW, Material.YELLOW_WOOL),
    ASSOCIATE("Associé", 3, NamedTextColor.WHITE, Material.WHITE_WOOL),
    GUEST("Invité", 4, NamedTextColor.GRAY, Material.GRAY_WOOL);

    private final String roleName;
    private final int roleId;
    private final NamedTextColor color;
    private final Material material;

    PlotRoles(String roleName, int roleId, NamedTextColor color, Material material) {
        this.roleName = roleName;
        this.roleId = roleId;
        this.color = color;
        this.material = material;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    public static PlotRoles[] getAllRoles(){
        return new PlotRoles[]{PlotRoles.CHIEF, PlotRoles.DEPUTY, PlotRoles.MEMBER, PlotRoles.ASSOCIATE, PlotRoles.GUEST};
    }

    public static PlotRoles[] getAllRolesExclude(PlotRoles... roles){
        PlotRoles[] allRoles = getAllRoles();
        PlotRoles[] rolesToReturn = new PlotRoles[allRoles.length - roles.length];
        int index = 0;
        for(PlotRoles role : allRoles){
            boolean isExcluded = false;
            for(PlotRoles excludedRole : roles){
                if(role.equals(excludedRole)){
                    isExcluded = true;
                    break;
                }
            }
            if(!isExcluded){
                rolesToReturn[index] = role;
                index++;
            }
        }
        return rolesToReturn;
    }

    public static Material[] getAllMaterials(){

        return new Material[]{Material.RED_WOOL,
                Material.ORANGE_WOOL,
                Material.YELLOW_WOOL,
                Material.WHITE_WOOL};
    }

}
