package net.whg;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin container for BotDown.
 */
public class BotDown extends JavaPlugin {

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled.");
    }
}
