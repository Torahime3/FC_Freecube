package fr.torahime.freecube.utils;

import fr.torahime.freecube.models.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

/**
 * @author Alexandre
 */
public class PlotIdentifier {

    public static boolean isInPlot(int blockX, int blockZ) {
        return Math.floorDiv(blockX, 16) % 8 != 0 && Math.floorDiv(blockZ, 16) % 8 != 0;
    }

    public static boolean isInPlot(Location location){
        return isInPlot(location.getBlockX(), location.getBlockZ());
    }

    public static boolean isPlotClaimed(int plotId){
        return Plot.getPlot(plotId) != null;
    }

    public static boolean isPlotClaimed(int blockX, int blockZ){
        return isPlotClaimed(getPlotIndex(blockX, blockZ));
    }

    public static boolean isPlotClaimed(Location location){
        return isPlotClaimed(getPlotIndex(location));
    }

    public static boolean isMemberOfPlot(int blockX, int blockZ, UUID player){
        if(!isInPlot(blockX, blockZ)){
            return false;
        }

        int plotIndex = getPlotIndex(blockX, blockZ);
        Plot plot = Plot.getPlot(plotIndex);
        if(plot == null){
            return false;
        }

        return plot.getOwner().equals(player) || plot.getMembers().contains(player);
    }

    public static int getPlotIndex(int blockX, int blockZ) {
        return getIndex(Math.floorDiv(blockX, 8 * 16), Math.floorDiv(blockZ, 8 * 16));
    }

    public static int getPlotIndex(Location location){
        return getPlotIndex(location.getBlockX(), location.getBlockZ());
    }

    private static int getIndex(int chunkX, int chunkZ) {
        int p;
        if (chunkZ * chunkZ >= chunkX * chunkX) {
            p = 4 * chunkZ * chunkZ - chunkZ - chunkX;
            if (chunkZ < chunkX) {
                p = p - 2 * (chunkZ - chunkX);
            }
        } else {
            p = 4 * chunkX * chunkX - chunkZ - chunkX;
            if (chunkZ < chunkX) {
                p = p + 2 * (chunkZ - chunkX);
            }
        }
        return p;
    }

    public static int[] getPlotChunkCoordinates(int id) {
        int[] chunkCoordinates = getCoordinates(id);
        return new int[] {chunkCoordinates[0] * 16 * 8, chunkCoordinates[1] * 16 * 8};
    }

    public static int[] getPlotCenterCoordinates(int id) {
        int[] chunkCoordinates = getCoordinates(id);
        return new int[] {chunkCoordinates[0] * 16 * 8 + 64, chunkCoordinates[1] * 16 * 8 + 64};
    }

    public static Location getPlotCenterLocation(int id) {
        int[] chunkCoordinates = getCoordinates(id);
        return new Location(Bukkit.getWorld("freecube"), chunkCoordinates[0] * 16 * 8 + 64, 0, chunkCoordinates[1] * 16 * 8 + 64);
    }

    private static int[] getCoordinates(int index) {
        index++;
        int k = (int) Math.ceil((Math.sqrt(index) - 1) / 2);
        int t = 2 * k;
        int m = (t + 1) * (t + 1);

        if (index >= m - t) {
            return new int[]{k - (m - index), -k};
        } else {
            m = m - t;
        }

        if (index >= m - t) {
            return new int[]{-k, -k + (m - index)};
        } else {
            m = m - t;
        }

        if (index >= m - t) {
            return new int[]{-k + (m - index), k};
        } else {
            return new int[]{k, k - (m - index - t)};
        }
    }

}
