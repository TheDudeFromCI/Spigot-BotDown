package net.whg;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import net.whg.match.MatchEvents;
import net.whg.match.MatchManager;
import net.whg.spawn.SpawnHubEvents;
import net.whg.util.Lang;

/**
 * The main plugin container for BotDown.
 */
public class BotDown extends JavaPlugin {

    private static Logger log;

    /**
     * Gets the logger associated with the BotDown plugin.
     * 
     * @return The logger, or null if the logger has no yet been initialized.
     */
    public static Logger log() {
        return log;
    }

    /**
     * Initializes the Bukkit plugin logger.
     * 
     * @param log - The logger.
     */
    private static void initializeLogger(Logger log) {
        BotDown.log = log;
    }

    private Lang lang;
    private Location spawnLocation;
    private MatchManager matchManager;

    @Override
    public void onDisable() {
        BotDown.log().info("Plugin disabled.");
    }

    @Override
    public void onEnable() {
        initializeLogger(getLogger());

        BotDown.log().info("Creating default config if not present.");
        saveDefaultConfig();

        loadTranslations();
        loadSpawnLocation();
        initializeMatchManager();

        registerEvents();

        BotDown.log().info("Plugin enabled.");
    }

    /**
     * Load the translations config file.
     */
    private void loadTranslations() {
        getLogger().info("Loading translations.");
        lang = new Lang(this, "translations.yml");
    }

    /**
     * Loads the spawn location from the config file.
     */
    private void loadSpawnLocation() {
        BotDown.log().info("Loading spawn location.");

        var config = getConfig();
        spawnLocation = config.getLocation("main.spawn");

        if (spawnLocation == null)
            throw new IllegalStateException("Spawn location not initialized!");
    }

    /**
     * Initializes the match manager.
     */
    private void initializeMatchManager() {
        BotDown.log().info("Loading match manager.");
        matchManager = new MatchManager();
    }

    /**
     * Registers all event listeners.
     */
    private void registerEvents() {
        BotDown.log().info("Registering events.");
        var pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new SpawnHubEvents(lang, spawnLocation), this);
        pluginManager.registerEvents(new MatchEvents(matchManager), this);
    }
}
