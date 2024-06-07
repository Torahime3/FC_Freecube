package fr.torahime.freecube.models.areamaker;

import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.models.plots.Plot;
import fr.torahime.freecube.utils.ItemBuilder;
import fr.torahime.freecube.utils.PlotIdentifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AreaMaker {

    protected Color color;
    protected int MAX_VOLUME;
    protected Location locationA;
    protected Location locationB;

    public AreaMaker(){
        this(null, null, 50, Color.WHITE);
    }

    public AreaMaker(Location locationA, Location locationB, int MAX_VOLUME, Color color){
        this.locationA = locationA;
        this.locationB = locationB;
        this.MAX_VOLUME = MAX_VOLUME;
        this.color = color;
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

    public int getA_X(){
        if (!isValidA()) throw new IllegalStateException("Location A is not set");
        return locationA.getBlockX();
    }

    public int getA_Y(){
        if (!isValidA()) throw new IllegalStateException("Location A is not set");
        return locationA.getBlockY();
    }

    public int getA_Z(){
        if (!isValidA()) throw new IllegalStateException("Location A is not set");
        return locationA.getBlockZ();
    }

    public int getB_X(){
        if (!isValidB()) throw new IllegalStateException("Location B is not set");
        return locationB.getBlockX();
    }

    public int getB_Y(){
        if (!isValidB()) throw new IllegalStateException("Location B is not set");
        return locationB.getBlockY();
    }

    public int getB_Z(){
        if (!isValidB()) throw new IllegalStateException("Location B is not set");
        return locationB.getBlockZ();
    }

    public Location getLocationA() {
        return locationA;
    }

    public Location getLocationB() {
        return locationB;
    }

    public Color getColor() {
        return color;
    }

    public int getMAX_VOLUME() {
        return MAX_VOLUME;
    }

    public boolean playerInArea(Player player){
        if(!isValid()) return false;
        return player.getLocation().getBlock().getLocation().getBlockX() >= Math.min(getA_X(), getB_X())
                && player.getLocation().getBlock().getLocation().getBlockX() <= Math.max(getA_X(), getB_X())
                && player.getLocation().getBlock().getLocation().getBlockY() >= Math.min(getA_Y(), getB_Y())
                && player.getLocation().getBlock().getLocation().getBlockY() <= Math.max(getA_Y(), getB_Y())
                && player.getLocation().getBlock().getLocation().getBlockZ() >= Math.min(getA_Z(), getB_Z())
                && player.getLocation().getBlock().getLocation().getBlockZ() <= Math.max(getA_Z(), getB_Z());
    }

    public void setLocationA(Location locationA) {
        this.locationA = locationA;
    }

    public void setLocationB(Location locationB) {
        this.locationB = locationB;
    }


    public boolean setLocation(Player player, LocationType locationType, Plot plot) {

        Location location = player.getLocation();

        if(location == null || plot == null){
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location A is null, not set.").color(NamedTextColor.RED)));
            return false; // Nouveau code d'erreur pour null input
        }

        if(PlotIdentifier.getPlotIndex(location) != plot.getId()){
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Location A is null, not set.").color(NamedTextColor.RED)));
            return false; // La location ne peut pas être en dehors de la zone concernée
        }

        if(location.equals(locationType == LocationType.A ? this.locationB : this.locationA)){
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("La position A a été définie.").color(NamedTextColor.WHITE)));
            return false; // La position A ne peut pas être la même que la position B
        }

        Location oldLocation = locationType == LocationType.A ? this.locationA : this.locationB;
        if (locationType == LocationType.A) {
            this.locationA = location;
        } else {
            this.locationB = location;
        }
        if (getTotalBlocks() > MAX_VOLUME) {
            if (locationType == LocationType.A) {
                this.locationA = oldLocation;
            } else {
                this.locationB = oldLocation;
            }
            player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text(String.format("La zone est trop grande. ( >%s)", this.MAX_VOLUME)).color(NamedTextColor.RED)));
            return false; // Zone trop grande
        }

        player.sendMessage(Component.text("[Freecube] ").color(NamedTextColor.GOLD).append(Component.text("Position " + locationType + " définie.").color(NamedTextColor.WHITE)));
        this.startParticleDisplay(player);
        return true; // Successful update

    }

    public int getTotalBlocks(){
        if(!isValid()) return -1;
        int lengthX = Math.abs(getB_X() - getA_X());
        int lengthY = Math.abs(getB_Y() - getA_Y());
        int lengthZ = Math.abs(getB_Z() - getA_Z());

        // Calcul du volume
        return lengthX * Math.max(lengthY, 1) * lengthZ;
    }

    public void startParticleDisplay(Player player) {

        AtomicReference<Integer> count = new AtomicReference<>(0);
        AtomicReference<Integer> task = new AtomicReference<>(0);
        task.set(Bukkit.getScheduler().runTaskTimer(Freecube.getInstance(), () -> {
            if (count.get() > 5) {
                Bukkit.getScheduler().cancelTask(task.get());
            }
            showParticles(player);
            count.set(count.get() + 1);
        }, 10, 15).getTaskId());
    }


    private void showParticles(Player player) {
        if (!isValid()) return;

        Particle.DustOptions dustOptions = new Particle.DustOptions(this.color, 1.5f);

        int x1 = locationA.getBlockX();
        int y1 = locationA.getBlockY();
        int z1 = locationA.getBlockZ();
        int x2 = locationB.getBlockX();
        int y2 = locationB.getBlockY();
        int z2 = locationB.getBlockZ();

        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                    if (x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2) {
                        player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0, 0, 0, 1, dustOptions);
                    }
                }
            }
        }
    }

    public ItemBuilder getLocationItemBuilder(LocationType locationType){

        boolean isValid = (locationType == LocationType.A) ? isValidA() : isValidB();
        Material bannerMaterial = isValid ? Material.GREEN_BANNER : Material.RED_BANNER;
        DyeColor borderColor = isValid ? DyeColor.GREEN : DyeColor.RED;
        String position = (locationType == LocationType.A) ? "A" : "B";

        int x = 0, y = 0, z = 0;
        if (isValid) {
            x = (locationType == LocationType.A) ? getA_X() : getB_X();
            y = (locationType == LocationType.A) ? getA_Y() : getB_Y();
            z = (locationType == LocationType.A) ? getA_Z() : getB_Z();
        }

        ItemBuilder itemBuilder = new ItemBuilder(bannerMaterial);

        if (locationType == LocationType.A) {
            itemBuilder.addPattern(
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
                    new Pattern(borderColor, PatternType.BORDER));
        } else {  // LocationType.B
            itemBuilder.addPattern(
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
                    new Pattern(borderColor, PatternType.CURLY_BORDER),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
                    new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
                    new Pattern(borderColor, PatternType.BORDER));
        }

        itemBuilder.applyPatterns();
        itemBuilder.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        itemBuilder.setDisplayName(Component.text("Position " + position + (isValid ? " X:" + x + " Y:" + y + " Z:" + z : ""))
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GOLD));
        itemBuilder.setLore(Component.text("> ")
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GREEN)
                .append(Component.text("Clic gauche pour définir la position " + position)
                        .decoration(TextDecoration.ITALIC, false)
                        .color(NamedTextColor.WHITE)));

        return itemBuilder;
    }


}
