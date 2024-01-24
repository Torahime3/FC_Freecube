package fr.torahime.freecube.models.entitys;

import fr.torahime.freecube.models.PlotStates;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class EntityGenerator {

    private Location locationA;
    private Location locationB;
    private PlotEntity entity;
    private PlotStates frequency;

    public EntityGenerator() {
        this(null, null, PlotEntity.PIG, PlotStates.LOW);
    }
    public EntityGenerator(Location locationA, Location locationB) {
        this(locationA, locationB, PlotEntity.PIG, PlotStates.LOW);
    }

    public EntityGenerator(Location locationA, Location locationB, PlotEntity entity) {
        this(locationA, locationB, entity, PlotStates.LOW);
    }
    public EntityGenerator(Location locationA, Location locationB, PlotEntity entity, PlotStates frequency) {
        this.locationA = locationA;
        this.locationB = locationB;
        this.entity = entity;
        this.frequency = frequency;
    }

    public void generateEntities(){
        if(!isValid()) return;

    }

    public boolean isValid(){
        return isValidA() && isValidB();
    }

    public boolean isValidA(){
        return locationA != null;
    }

    public boolean isValidB(){
        return locationB != null;
    }

    public boolean setLocationA(Location locationA) {
        if(locationA != null && locationB != null && locationA.getBlock().getLocation() == locationB.getBlock().getLocation()){
            return false;
        }
        this.locationA = locationA;
        return true;
    }

    public boolean setLocationB(Location locationB) {
        if(locationA != null && locationB != null && locationA.getBlock().getLocation() == locationB.getBlock().getLocation()){
            return false;
        }
        this.locationB = locationB;
        return true;
    }

    public int getA_X(){ return locationA.getBlockX();}

    public int getA_Y(){
        return locationA.getBlockY();
    }

    public int getA_Z(){
        return locationA.getBlockZ();
    }

    public int getB_X(){
        return locationB.getBlockX();
    }

    public int getB_Y(){
        return locationB.getBlockY();
    }

    public int getB_Z(){
        return locationB.getBlockZ();
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

    public int getTotalBlocks(){
        if(!isValid()) return -1;
        int deltaX = Math.abs(getB_X() - getA_X());
        int deltaY = Math.abs(getB_Y() - getA_Y());
        int deltaZ = Math.abs(getB_Z() - getA_Z());

        int nombreDePoints = Math.max(deltaX, Math.max(deltaY, deltaZ));
        return nombreDePoints;
    }
}
