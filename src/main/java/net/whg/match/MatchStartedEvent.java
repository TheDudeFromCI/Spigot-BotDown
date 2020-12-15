package net.whg.match;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that is called when a match is started.
 */
public class MatchStartedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final Match match;

    /**
     * Creates a new match started event.
     * 
     * @param match - The match that was started.
     */
    MatchStartedEvent(Match match) {
        this.match = match;
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    /**
     * Gets the match that was started.
     * 
     * @return The match.
     */
    public Match getMatch() {
        return match;
    }
}
