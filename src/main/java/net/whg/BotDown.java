package net.whg;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.whg.match.MatchEvents;
import net.whg.match.MatchManager;
import net.whg.spawn.SpawnManager;
import net.whg.util.Lang;
import net.whg.world.PreventSaveEvent;

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
    private MatchManager matchManager;
    private SpawnManager spawnManager;

    @Override
    public void onEnable() {
        initializeLogger(getLogger());
        saveDefaultConfig();

        lang = new Lang(this, "translations.yml");
        spawnManager = new SpawnManager(this);
        initializeMatchManager();

        registerEvents();
        disableWorldSaving();

        BotDown.log().info("Plugin enabled.");
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

        pluginManager.registerEvents(new MatchEvents(matchManager), this);
        pluginManager.registerEvents(new PreventSaveEvent(), this);
    }

    /**
     * This disables auto save on the main world to prevent the world from being
     * edited as a result of the match creation.
     */
    private void disableWorldSaving() {
        BotDown.log().info("Disabling world auto save.");

        var world = Bukkit.getWorld("world");
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
    }

    /**
     * Gets the plugin language messages.
     * 
     * @return The language translation config, or null if the plugin has not yet
     *         been enabled.
     */
    public Lang getLang() {
        return lang;
    }

    /**
     * Gets the plugin spawn manager.
     * 
     * @return The spawn manager.
     */
    public SpawnManager getSpawnManager() {
        return spawnManager;
    }
}
