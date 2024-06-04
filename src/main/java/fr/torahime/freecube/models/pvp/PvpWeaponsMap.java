package fr.torahime.freecube.models.pvp;

import fr.torahime.freecube.models.plots.PlotStates;

import java.util.HashMap;

public class PvpWeaponsMap extends HashMap<PvpWeapons, PlotStates> {

    public PvpWeaponsMap() {
        for (PvpWeapons weapon : PvpWeapons.values()) {
            this.put(weapon, PlotStates.DEACTIVATE);
        }
    }

}
