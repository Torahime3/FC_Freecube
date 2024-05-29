package fr.torahime.freecube.models.pvp;

import fr.torahime.freecube.models.AreaMaker;
import org.bukkit.Color;
import org.bukkit.Location;

public class PvpArea extends AreaMaker {

    private boolean meleeWeapon;
    private boolean rangeWeapon;

    public PvpArea(){
        this(null, null);
    }

    public PvpArea(Location locationA, Location locationB){
        super(locationA, locationB, 600, Color.RED);
        this.meleeWeapon = false;
        this.rangeWeapon = false;
    }

    public boolean isPvpEnabled() {
        return isMeleeWeapon() || isRangeWeapon();
    }

    public boolean isMeleeWeapon() {
        return this.meleeWeapon;
    }

    public void setMeleeWeapon(Boolean meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public boolean isRangeWeapon() {
        return this.rangeWeapon;
    }

    public void setRangeWeapon(Boolean rangeWeapon) {
        this.rangeWeapon = rangeWeapon;
    }
}
