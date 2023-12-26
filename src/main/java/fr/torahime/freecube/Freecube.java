package fr.torahime.freecube;

import fr.torahime.freecube.commands.players.FindCommand;
import fr.torahime.freecube.commands.players.ClaimCommand;
import fr.torahime.freecube.commands.players.MainCommand;
import fr.torahime.freecube.controllers.PlotChunkGenerator;
import fr.torahime.freecube.listeners.BlockActionListener;
import fr.torahime.freecube.listeners.PlayerInteractListener;
import fr.torahime.freecube.listeners.PlayerJoinListener;
import fr.torahime.freecube.listeners.PlotEnterListener;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Freecube extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("Freecube plugin enabled");

        initWorld();
        initListeners();
        initCommands();
    }

    @Override
    public void onDisable() {

        this.getLogger().info("Freecube plugin stopped");
    }

    public void initCommands(){
        MainCommand mc = new MainCommand();

        Objects.requireNonNull(getCommand("fc")).setExecutor(mc);
        mc.addCommandExecutor("claim",new ClaimCommand());
        mc.addCommandExecutor("find", new FindCommand());

    }

    public void initListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new BlockActionListener(), this);
        getServer().getPluginManager().registerEvents(new PlotEnterListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }

    public void initWorld(){

        if (this.getServer().getWorld("freecube") == null) {

            this.getLogger().info("freecube world not found, creating...");

            WorldCreator wc = new WorldCreator("freecube", new NamespacedKey(this, "freecube"))
                    .environment(World.Environment.NORMAL)
                    .type(WorldType.FLAT)
                    .generateStructures(false)
                    .generator(new PlotChunkGenerator(this));

            World world = this.getServer().createWorld(wc);
            if(world != null){
                world.setDifficulty(Difficulty.PEACEFUL);
                world.save();

            }

            this.getLogger().info("freecube world created");

        }

    }

}

