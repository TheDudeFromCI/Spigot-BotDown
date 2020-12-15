package net.whg.match;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.whg.util.PartialReadonlyList;

/**
 * Represents an active match.
 */
class Match {
    private final PartialReadonlyList<Player> players = new PartialReadonlyList<>();
    private final UUID uuid;
    private boolean active = false;

    /**
     * Creates a new, empty match object.
     */
    Match() {
        uuid = UUID.randomUUID();
    }

    /**
     * Adds a new player to this match.
     */
    void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes a player from this match.
     */
    void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Gets a list of all players currently in this match.
     * 
     * @return A read-only list of all players.
     */
    public List<Player> getPlayers() {
        return players.readonly();
    }

    /**
     * Starts this match.
     */
    void start() {
        active = true;
    }

    /**
     * Ends this match.
     */
    void end() {
        active = false;
        players.clear();
    }

    /**
     * Gets whether or not this match is currently active.
     * 
     * @return True if this match is active, false if the match has not yet started,
     *         or has already ended.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the UUID of this match.
     * 
     * @return The uuid.
     */
    public UUID getUUID() {
        return uuid;
    }
}
