package fr.torahime.freecube.models.preferences;

import java.util.HashMap;

public class PreferencesMap extends HashMap<Preferences, PlotStates> {

    public PreferencesMap(){
        this.put(Preferences.FLY, PlotStates.ACTIVATE);
        this.put(Preferences.GAMEMODE, PlotStates.CREATIVE);
        this.put(Preferences.CLEARINVENTORY, PlotStates.DEACTIVATE);
        this.put(Preferences.SPAWNTP, PlotStates.DEACTIVATE);
        this.put(Preferences.DROPITEMS, PlotStates.ACTIVATE);
        this.put(Preferences.LOOTITEMS, PlotStates.ACTIVATE);
    }
}
