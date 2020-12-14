package net.whg.spawn;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.whg.util.Lang;

/**
 * Events related to the server spawn hub.
 */
public class SpawnHubEvents implements Listener {
    private final Lang lang;
    private final Location spawnLocation;

    /**
     * Creates a new spawn hub manager.
     */
    public SpawnHubEvents(Lang lang, Location spawnLocation) {
        this.lang = lang;
        this.spawnLocation = spawnLocation;
    }

    /**
     * Listens for when a player joins to send them a welcome message, set them in
     * spectator mode, clear their inventory, and send them to spawn.
     * 
     * @param event - The event that was called.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        sendWelcomeMessage(player);
        sendToSpawn(player);
        clearInventory(player);
    }

    /**
     * Sends a welcome message to the player.
     * 
     * @param player - The player to send the message to.
     */
    private void sendWelcomeMessage(Player player) {
        var message = lang.getRawMessage("main.welcome", player.getLocale());
        player.spigot().sendMessage(message);
    }

    /**
     * Puts the player in spectator mode and sends them to spawn.
     * 
     * @param player - The player.
     */
    private void sendToSpawn(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(spawnLocation);
    }

    /**
     * Clears the players inventory and all levels, scoreboards, and status effects.
     */
    private void clearInventory(Player player) {
        player.getInventory().clear();
        player.setLevel(0);
        player.setExp(0);
        player.setHealth(20);
        player.setFireTicks(0);

        for (var effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        var mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        player.setScoreboard(mainScoreboard);
    }
}
