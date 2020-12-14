package net.whg;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.match.MatchManager;
import net.whg.spawn.SpawnManager;
import net.whg.util.Lang;
import net.whg.world.WorldManager;

/**
 * The main plugin container for BotDown.
 */
public class BotDown extends JavaPlugin {
    private Lang lang;
    private MatchManager matchManager;
    private SpawnManager spawnManager;
    private WorldManager worldManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        lang = new Lang(this, "translations.yml");
        spawnManager = new SpawnManager(this);
        matchManager = new MatchManager(this);
        worldManager = new WorldManager(this);

        getLogger().info("Plugin enabled.");
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

    /**
     * Gets the plugin match manager.
     * 
     * @return The match manager.
     */
    public MatchManager getMatchManager() {
        return matchManager;
    }

    /**
     * Gets the plugin world manager.
     * 
     * @return The world manager.
     */
    public WorldManager getWorldManager() {
        return worldManager;
    }
}
