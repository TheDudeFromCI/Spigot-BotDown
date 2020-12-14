package net.whg.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * Represents an active match.
 */
public class Match {
    private final ArrayList<Player> players = new ArrayList<>();
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
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes a player from this match.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Gets a list of all players currently in this match.
     * 
     * @return A read-only list of all players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
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
