package fr.torahime.freecube;

import fr.torahime.freecube.commands.MainCommand;
import fr.torahime.freecube.commands.admins.plots.ReadCommand;
import fr.torahime.freecube.commands.admins.plots.SaveCommand;
import fr.torahime.freecube.commands.admins.ranks.SetRankCommand;
import fr.torahime.freecube.commands.players.*;
import fr.torahime.freecube.commands.players.plots.*;
import fr.torahime.freecube.controllers.generators.PlotChunkGenerator;
import fr.torahime.freecube.listeners.players.*;
import fr.torahime.freecube.listeners.plots.PlotPvpListener;
import fr.torahime.freecube.listeners.plots.PlotBoundaryListener;
import fr.torahime.freecube.listeners.worldsettings.*;
import fr.torahime.freecube.listeners.plots.PlotInteractionsListener;
import fr.torahime.freecube.listeners.plots.PlotPreferencesListener;
import fr.torahime.freecube.utils.ANSIColors;
import fr.torahime.freecube.utils.Dotenv;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Freecube extends JavaPlugin {

    private static Freecube freecube;

    public static Plugin getInstance() {
        return freecube;
    }

    @Override
    public void onEnable() {
        freecube = this;

        initWorld();
        initListeners();
        initCommands();

        if(Dotenv.get("PRODUCTION") == null){
            this.getLogger().info(ANSIColors.YELLOW + "DEVELOPMENT ENVIRONMENT, LOADING .ENV FILE" + ANSIColors.RESET);
            Dotenv.load();
        }

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
        mc.addCommandExecutor("setname", new SetNameCommand());
        mc.addCommandExecutor("setrank", new SetRankCommand());

        //Admin command
        mc.addCommandExecutor("save", new SaveCommand());
        mc.addCommandExecutor("read", new ReadCommand());


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
        getServer().getPluginManager().registerEvents(new PlayerPlotSaverListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerOpenFCMainMenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlotPvpListener(), this);

        //Preferences
        getServer().getPluginManager().registerEvents(new PlotPreferencesListener(), this);

        //Interactions
        getServer().getPluginManager().registerEvents(new PlotInteractionsListener(), this);

        //Game Settings
        getServer().getPluginManager().registerEvents(new WorldExplosionListener(), this);
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

