package fr.torahime.freecube.models.entitys;

import fr.torahime.freecube.models.areamaker.AreaMaker;
import fr.torahime.freecube.models.game.PlotStates;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class EntityGenerator extends AreaMaker {

    private final int MAX_ENTITY = 5;
    private ArrayList<EntityType> spawnedEntitys = new ArrayList<>();
    private PlotEntity entity;
    private PlotStates frequency;

    public EntityGenerator() {
        this(null, null, 300, PlotEntity.COW, PlotStates.LOW);
    }

    public EntityGenerator(Location locationA, Location locationB, PlotEntity entity) {
        this(locationA, locationB, 300, entity, PlotStates.LOW);
    }

    public EntityGenerator(Location locationA, Location locationB, int MAX_VOLUME, PlotEntity entity, PlotStates frequency) {
        super(locationA, locationB, MAX_VOLUME, Color.AQUA);
        this.entity = entity;
        this.frequency = frequency;
    }

    public void generateEntities(){
        if(!isValid()) return;

        locationA.getWorld().spawnEntity(locationA, EntityType.COW);

    }

    public ArrayList<EntityType> getSpawnedEntitys() {
        return spawnedEntitys;
    }

    public void addSpawnedEntity(EntityType entityType){
        if(spawnedEntitys.size() < this.MAX_ENTITY){
            spawnedEntitys.add(entityType);
        }
    }


    public PlotEntity getEntity() {
        return entity;
    }

    public void setEntity(PlotEntity entity) {
        this.entity = entity;
    }

    public void setFrequency(PlotStates frequency) {
        this.frequency = frequency;
    }

    public PlotStates getFrequency() {
        return frequency;
    }

}
