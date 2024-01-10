package fr.torahime.freecube.models.roles;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public enum PlotRoles {

    CHIEF("Chef", 0, NamedTextColor.RED, Material.RED_WOOL,
            new String[]{"Les permissions du rang précédent",
                    "Changer le spawn",
                    "Changer l'heure",
                    "Virer des adjoints",
                    "Changer le rang des adjoints / donnerle rang adjoint",
                    "Changer le nom"}, null),
    DEPUTY("Adjoint", 1, NamedTextColor.GOLD, Material.ORANGE_WOOL,
            new String[]{"Les permissions du rang précédent",
                    "Bannir un joueur de la zone",
                    "Inviter des joueurs",
                    "Changer les préférences",
                    "Changer les intéractions",
                    "Virer des associés et membres",
                    "Changer le rang des associés et membres / donner les rangs associé et membre"}, PlotRoles.CHIEF),

    MEMBER("Membre", 2, NamedTextColor.YELLOW, Material.YELLOW_WOOL,
            new String[]{"Les permissions du rang précédent",
                    "Construire",
                    "éjecter un joueur de la zone"}, PlotRoles.DEPUTY),
    ASSOCIATE("Associé", 3, NamedTextColor.WHITE, Material.WHITE_WOOL,
            new String[]{"Ignorer les préférences de la zone (être en mode créatif, etc)",
                    "Intéragir avec tous les blocs"}, PlotRoles.DEPUTY),
    GUEST("Invité", 4, NamedTextColor.GRAY, Material.GRAY_WOOL, new String[]{}, PlotRoles.DEPUTY);

    private final String roleName;
    private final int roleId;
    private final NamedTextColor color;
    private final Material material;
    private final String[] permissionsLiteral;
    private final PlotRoles roleCanBeGivenBy;

    PlotRoles(String roleName, int roleId, NamedTextColor color, Material material, String[] permissionsLiteral, PlotRoles roleCanBeGivenBy) {
        this.roleName = roleName;
        this.roleId = roleId;
        this.color = color;
        this.material = material;
        this.permissionsLiteral = permissionsLiteral;
        this.roleCanBeGivenBy = roleCanBeGivenBy;
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

    public  String[] getPermissionsLiteral(){
        return permissionsLiteral;
    }

    public PlotRoles getRoleCanBeGivenBy() {
        return roleCanBeGivenBy;
    }

    public static PlotRoles[] getAllRoles(){
        return PlotRoles.values();
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
