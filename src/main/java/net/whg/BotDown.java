package net.whg;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.spawn.SpawnHubEvents;
import net.whg.util.Lang;

/**
 * The main plugin container for BotDown.
 */
public class BotDown extends JavaPlugin {

    private Lang lang;

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    @Override
    public void onEnable() {
        loadTranslations();
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
     * Registers all event listeners.
     */
    private void registerEvents() {
        var pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SpawnHubEvents(lang), this);
    }
}
