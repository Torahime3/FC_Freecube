package fr.torahime.freecube.controllers.generators;

import org.bukkit.Material;
import org.bukkit.block.data.type.Slab;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PlotChunkGenerator extends ChunkGenerator {

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

        boolean isPathX = chunkX % 8 == 0;
        boolean isPathZ = chunkZ % 8 == 0;
        boolean isPath = isPathX || isPathZ;

        //Draw roads
        for (int x = 0; x < 16; x++) {

            for (int z = 0; z < 16; z++){

                chunkData.setBlock(x,50, z, isPath ? Material.STONE : Material.GRASS_BLOCK);

                for(int y = 0; y < 50; y++){
                    chunkData.setBlock(x, y, z, Material.DIRT);
                }
            }
        }

        //Create slab material
        Slab slabData = (Slab) Material.SMOOTH_STONE_SLAB.createBlockData();
        slabData.setType(Slab.Type.DOUBLE);

        //Draw roads borders
        if(isPathX && !isPathZ){
            for(int z = 0; z < 16; z++){
                chunkData.setBlock(0, 50, z, slabData);
                chunkData.setBlock(15, 50, z, slabData);
                chunkData.setBlock(0, 50, z, slabData);
            }
        }

        if(isPathZ && !isPathX){
            for(int x = 0; x < 16; x++){
                chunkData.setBlock(x, 50, 0, slabData);
                chunkData.setBlock(x, 50, 15, slabData);
            }
        }

        if(isPathX && isPathZ){
                chunkData.setBlock(0, 50, 0, slabData);
                chunkData.setBlock(0, 50, 15, slabData);
                chunkData.setBlock(15, 50, 0, slabData);
                chunkData.setBlock(15, 50, 15, slabData);
        }
    }

}
