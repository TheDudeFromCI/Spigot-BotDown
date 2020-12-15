package net.whg.match;

import org.bukkit.entity.Player;

/**
 * Thrown if a player attempts to join a match while already in another match or
 * in the queue.
 */
public class AlreadyInMatchException extends RuntimeException {
    private static final long serialVersionUID = -4215189266589419149L;

    /**
     * Creates a new AlreadyInMatchException.
     * 
     * @param player - The player who is in the queue or match.
     * @param queue  - Whether the player is in a queue or a match.
     */
    public AlreadyInMatchException(Player player, boolean queue) {
        super(String.format("%s is already in %s!", player.getName(), queue ? "the queue" : "a match"));
    }
}
