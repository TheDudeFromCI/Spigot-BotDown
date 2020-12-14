package net.whg;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.match.MatchManager;
import net.whg.spawn.SpawnManager;
import net.whg.util.Lang;
import net.whg.world.WorldManager;

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
    private WorldManager worldManager;

    @Override
    public void onEnable() {
        initializeLogger(getLogger());
        saveDefaultConfig();

        lang = new Lang(this, "translations.yml");
        spawnManager = new SpawnManager(this);
        matchManager = new MatchManager(this);
        worldManager = new WorldManager(this);

        BotDown.log().info("Plugin enabled.");
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
