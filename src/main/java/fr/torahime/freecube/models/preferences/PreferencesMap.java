package fr.torahime.freecube.models.preferences;

import fr.torahime.freecube.models.PlotStates;

import java.util.HashMap;

public class PreferencesMap extends HashMap<Preference, PlotStates> {

    public PreferencesMap(){
        this.put(Preference.FLY, PlotStates.ACTIVATE);
        this.put(Preference.GAMEMODE, PlotStates.CREATIVE);
        this.put(Preference.CLEARINVENTORY, PlotStates.DEACTIVATE);
        this.put(Preference.SPAWNTP, PlotStates.DEACTIVATE);
        this.put(Preference.DROPITEMS, PlotStates.ACTIVATE);
        this.put(Preference.LOOTITEMS, PlotStates.ACTIVATE);
    }

}
