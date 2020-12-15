package net.whg.match;

/**
 * A minigame type that can be competed in.
 */
public interface Minigame {
    /**
     * Gets the name of this minigame.
     * 
     * @return The name.
     */
    String getName();

    /**
     * Gets the required number of players to play this game.
     * 
     * @return The player count.
     */
    int getPlayerCount();
}
