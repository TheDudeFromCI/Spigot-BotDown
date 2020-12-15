package net.whg.match;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listeners for events related to match management and handling.
 */
public class MatchEvents implements Listener {
    private final MatchManager matchManager;
    private final World world;

    /**
     * Creates a new match event listener.
     * 
     * @param matchManager - The match manager this listener is manipulating.
     * @param world        - The world the matches are taking place on.
     */
    public MatchEvents(MatchManager matchManager, World world) {
        this.matchManager = matchManager;
        this.world = world;
    }

    /**
     * Called when a player quits the server to clean up lingering match events.
     * 
     * @param event - The event that was triggered.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        matchManager.removeFromAllMatches(player);
        matchManager.removeFromQueue(player);
    }

    @EventHandler
    public void onMatchStart(MatchStartedEvent event) {
        var match = event.getMatch();
        var location = new Location(world, match.x() + match.size() / 2, 65, match.z() + match.size() / 2);
        for (var player : match.getPlayers()) {
            player.teleport(location);
        }
    }
}
