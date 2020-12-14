package net.whg.spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.whg.util.Lang;

/**
 * Events related to the server spawn hub.
 */
public class SpawnHubEvents implements Listener {
    private final Lang lang;

    /**
     * Creates a new spawn hub manager.
     */
    public SpawnHubEvents(Lang lang) {
        this.lang = lang;
    }

    /**
     * Listens for when a player joins to send them a welcome message.
     * 
     * @param event - The event that was called.
     */
    @EventHandler
    public void sendWelcomeMessage(PlayerJoinEvent event) {
        var player = event.getPlayer();
        player.spigot().sendMessage(lang.getRawMessage("main.welcome", player.getLocale()));
    }
}
