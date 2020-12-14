package net.whg.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.whg.BotDown;

/**
 * Handles everything related to the world spawn hub.
 */
public class SpawnManager {
    private final Location spawnLocation;

    /**
     * Creates a new spawn manager and initializes event listeners.
     * 
     * @param plugin - The BotDown plugin instance.
     */
    public SpawnManager(BotDown plugin) {
        var config = plugin.getConfig();
        spawnLocation = config.getLocation("world.spawn");

        var pluginManager = Bukkit.getPluginManager();
        var events = new SpawnHubEvents(plugin.getLang(), spawnLocation);
        pluginManager.registerEvents(events, plugin);

        var worldName = config.getString("world.name");
        var world = Bukkit.getWorld(worldName);
        world.setSpawnLocation(spawnLocation);
    }

    /**
     * Gets the world spawn location.
     * 
     * @return The spawn location.
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
