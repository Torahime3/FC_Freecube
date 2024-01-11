package fr.torahime.freecube.models.interactions;

import fr.torahime.freecube.models.PlotStates;

import java.util.HashMap;

public class InteractionsMap extends HashMap<Interactions, PlotStates> {

    public InteractionsMap(){
        for(Interactions interaction : Interactions.values()){
            this.put(interaction, PlotStates.ACTIVATE);
        }
    }

}
