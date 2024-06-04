package fr.torahime.freecube.models.pvp;

import fr.torahime.freecube.models.areamaker.AreaMaker;
import fr.torahime.freecube.models.plots.PlotStates;
import org.bukkit.Color;
import org.bukkit.Location;

public class PvpArea extends AreaMaker {

    private PvpWeaponsMap pvpWeaponsMap = new PvpWeaponsMap();

    public PvpArea(){
        this(null, null);
    }

    public PvpArea(Location locationA, Location locationB){
        super(locationA, locationB, 600, Color.RED);
    }

    public PvpWeaponsMap getPvpWeaponsMap() {
        return pvpWeaponsMap;
    }

    public boolean isPvpEnabled() {
        return pvpWeaponsMap.values().stream().anyMatch(plotState -> plotState == PlotStates.ACTIVATE);
    }
}
