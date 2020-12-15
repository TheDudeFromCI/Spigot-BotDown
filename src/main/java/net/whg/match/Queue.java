package net.whg.match;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A queued list of bots that are waiting to join a match.
 */
class Queue {
    private final List<BotEntry> entries = new ArrayList<>();
    private final MatchManager matchManager;
    private final Logger log;

    /**
     * Creates a new queue.
     * 
     * @param matchManager - The match manager.
     * @param log          - The logger for this object.
     */
    Queue(MatchManager matchManager, Logger log) {
        this.matchManager = matchManager;
        this.log = log;
    }

    /**
     * Adds a new bot entry to the end of this queue.
     * 
     * @param entry - The bot entry.
     * @throws AlreadyInMatchException if the player is already in the queue.
     */
    void addEntry(BotEntry entry) {
        var inMatch = entries.stream().anyMatch(e -> e.getBot() == entry.getBot());
        if (inMatch)
            throw new AlreadyInMatchException(entry.getBot(), true);

        entries.add(entry);
        checkForStart(entry.getMinigame());
    }

    /**
     * Checks to see if the given minigame now has enough players to begin.
     * 
     * @param minigame - The minigame to check for.
     */
    private void checkForStart(Minigame minigame) {
        var count = entries.stream().filter(e -> e.getMinigame() == minigame).count();
        if (count < minigame.getPlayerCount())
            return;

        log.info("Starting match for " + minigame.getName());
        var match = matchManager.createMatch();

        for (var entry : entries) {
            if (entry.getMinigame() == minigame)
                match.addPlayer(entry.getBot());
        }

        entries.removeIf(e -> e.getMinigame() == minigame);

        var matchStartedEvent = new MatchStartedEvent(match);
        Bukkit.getPluginManager().callEvent(matchStartedEvent);
    }

    /**
     * Removes a player from the queue if they are currently in the queue.
     * 
     * @param player - The player to remove.
     */
    void removePlayer(Player player) {
        entries.removeIf(e -> e.getBot() == player);
    }
}
