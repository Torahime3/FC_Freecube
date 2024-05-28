package fr.torahime.freecube;

import fr.torahime.freecube.commands.players.*;
import fr.torahime.freecube.commands.players.plots.*;
import fr.torahime.freecube.controllers.PlotChunkGenerator;
import fr.torahime.freecube.listeners.*;
import fr.torahime.freecube.listeners.plots.DamageListener;
import fr.torahime.freecube.listeners.worldsettings.*;
import fr.torahime.freecube.listeners.plots.InteractionsListener;
import fr.torahime.freecube.listeners.plots.PreferencesListener;
import fr.torahime.freecube.utils.Dotenv;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.logging.Logger;

public final class Freecube extends JavaPlugin {

    public static Plugin getInstance() {
        return Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Freecube"));
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Freecube plugin enabled");

        initWorld();
        initListeners();
        initCommands();

        Dotenv.load(this.getLogger());
    }

    @Override
    public void onDisable() {

        this.getLogger().info("Freecube plugin stopped");
    }

    public void initCommands(){
        //Main command (/fc <subcommand>)
        MainCommand mc = new MainCommand();
        Objects.requireNonNull(getCommand("fc")).setExecutor(mc);
        mc.addCommandExecutor("claim",new ClaimCommand());
        mc.addCommandExecutor("find", new FindCommand());
        mc.addCommandExecutor("invite", new InviteCommand());
        mc.addCommandExecutor("accept", new AcceptCommand());
        mc.addCommandExecutor("setspawn", new SetSpawnCommand());
        mc.addCommandExecutor("save", new SaveCommand());


        //Teleport command (/tp <subcommand>)
        TeleportCommand tc = new TeleportCommand();
        Objects.requireNonNull(getCommand("tp")).setExecutor(tc);

        //Private message command
        PrivateMessageCommand pmc = new PrivateMessageCommand();
        Objects.requireNonNull(getCommand("msg")).setExecutor(pmc);
        Objects.requireNonNull(getCommand("r")).setExecutor(pmc);
    }

    public void initListeners(){
        getServer().getPluginManager().registerEvents(new PlayerSessionListener(), this);
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlotBoundaryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);

        //Preferences
        getServer().getPluginManager().registerEvents(new PreferencesListener(), this);

        //Interactions
        getServer().getPluginManager().registerEvents(new InteractionsListener(), this);

        //Game Settings
        getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
    }

    public void initWorld(){

        if (this.getServer().getWorld("freecube") == null) {

            this.getLogger().info("freecube world not found, creating...");

            WorldCreator wc = new WorldCreator("freecube", new NamespacedKey(this, "freecube"))
                    .environment(World.Environment.NORMAL)
                    .type(WorldType.FLAT)
                    .generateStructures(false)
                    .generator(new PlotChunkGenerator());

            //Change gamerule announceAdvancements to false

            World world = this.getServer().createWorld(wc);

            if(world != null){
                world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
                world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
                world.setGameRule(GameRule.DO_FIRE_TICK, false);
                world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
                world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
                world.setDifficulty(Difficulty.PEACEFUL);
                world.save();

            }

            this.getLogger().info("freecube world created");

        }

    }

}

