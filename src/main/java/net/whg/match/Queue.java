package net.whg.match;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A queued list of bots that are waiting to join a match.
 */
public class Queue {
    private final List<BotEntry> entries = new ArrayList<>();
    private final Logger log;

    /**
     * Creates a new queue.
     * 
     * @param log - The logger for this object.
     */
    Queue(Logger log) {
        this.log = log;
    }

    /**
     * Adds a new bot entry to the end of this queue.
     * 
     * @param entry - The bot entry.
     */
    public void addEntry(BotEntry entry) {
        entries.add(entry);
        checkForStart(entry.getMinigame());
    }

    private void checkForStart(Minigame minigame) {
        var count = 0;
        for (var entry : entries)
            if (entry.getMinigame() == minigame)
                count++;

        if (count < minigame.getPlayerCount())
            return;

        var participants = new ArrayList<BotEntry>();
        for (var entry : entries) {
            if (entry.getMinigame() == minigame)
                participants.add(entry);
        }

        log.info("Starting match for " + minigame.getName());
    }
}
