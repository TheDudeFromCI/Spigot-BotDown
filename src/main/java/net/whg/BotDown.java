package net.whg;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import net.whg.spawn.SpawnHubEvents;
import net.whg.util.Lang;

/**
 * The main plugin container for BotDown.
 */
public class BotDown extends JavaPlugin {

    private Lang lang;
    private Location spawnLocation;

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadTranslations();
        loadSpawnLocation();

        registerEvents();

        getLogger().info("Plugin enabled.");
    }

    /**
     * Load the translations config file.
     */
    private void loadTranslations() {
        lang = new Lang(this, "translations.yml");
    }

    /**
     * Loads the spawn location from the config file.
     */
    private void loadSpawnLocation() {
        var config = getConfig();
        spawnLocation = config.getLocation("main.spawn");

        if (spawnLocation == null)
            throw new IllegalStateException("Spawn location not initialized!");
    }

    /**
     * Registers all event listeners.
     */
    private void registerEvents() {
        var pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SpawnHubEvents(lang, spawnLocation), this);
    }
}
