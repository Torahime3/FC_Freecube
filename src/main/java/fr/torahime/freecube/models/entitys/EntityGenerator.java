package fr.torahime.freecube.models.entitys;

import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.models.AreaMaker;
import fr.torahime.freecube.models.PlotStates;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class EntityGenerator extends AreaMaker {

    private final int MAX_ENTITY = 5;
    private ArrayList<EntityType> spawnedEntitys = new ArrayList<>();
    private PlotEntity entity;
    private PlotStates frequency;

    public EntityGenerator() {
        this(null, null, 300, PlotEntity.COW, PlotStates.LOW);
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
