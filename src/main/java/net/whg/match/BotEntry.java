package net.whg.match;

import org.bukkit.entity.Player;

/**
 * Represents a bot that is waiting to join a match.
 */
public class BotEntry {
    private final Player bot;
    private final String botMaker;
    private final Minigame minigame;

    /**
     * Creates a new bot entry.
     * 
     * @param bot      - The bot being queued.
     * @param botMaker - The player who made this bot.
     * @param minigame - The minigame that the bot is queuing for.
     */
    public BotEntry(Player bot, String botMaker, Minigame minigame) {
        this.bot = bot;
        this.botMaker = botMaker;
        this.minigame = minigame;
    }

    /**
     * Gets the bot entry.
     * 
     * @return The bot.
     */
    public Player getBot() {
        return bot;
    }

    /**
     * Gets the name of the player that made this bot.
     * 
     * @return The bot maker.
     */
    public String getBotMaker() {
        return botMaker;
    }

    /**
     * Gets the minigame that the bot is queuing to play.
     * 
     * @return The minigame.
     */
    public Minigame getMinigame() {
        return minigame;
    }
}
