package net.whg.world;

import org.bukkit.Bukkit;

import net.whg.BotDown;

public class WorldManager {
    public WorldManager(BotDown plugin) {
        var worldName = plugin.getConfig().getString("world.name");
        var world = Bukkit.getWorld(worldName);

        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);

        var pluginManager = Bukkit.getPluginManager();
        var events = new PreventSaveEvent();
        pluginManager.registerEvents(events, plugin);
    }
}
