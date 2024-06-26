package fr.torahime.freecube.models.interactions;

import fr.torahime.freecube.models.plots.PlotStates;

import java.util.HashMap;

public class InteractionsMap extends HashMap<Interaction, PlotStates> {

    public InteractionsMap(){
        for(Interaction interaction : Interaction.values()){
                this.put(interaction, interaction.getDefaultPlotState());
        }
    }

}
