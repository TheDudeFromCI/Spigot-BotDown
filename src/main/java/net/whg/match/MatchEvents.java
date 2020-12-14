package net.whg.match;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listeners for events related to match management and handling.
 */
public class MatchEvents implements Listener {
    private final MatchManager matchManager;

    /**
     * Creates a new match event listener.
     * 
     * @param matchManager - The match manager this listener is manipulating.
     */
    public MatchEvents(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    /**
     * Called when a player quits the server to clean up lingering match events.
     * 
     * @param event - The event that was triggered.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        removeFromAllMatches(player);
    }

    /**
     * Removes the given player from all matches they are currently in.
     * 
     * @param player - The player to remove.
     */
    private void removeFromAllMatches(Player player) {
        for (var match : matchManager.findPlayer(player)) {
            match.removePlayer(player);
        }
    }
}