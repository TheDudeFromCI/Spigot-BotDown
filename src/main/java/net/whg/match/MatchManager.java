package net.whg.match;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.whg.BotDown;

/**
 * Manages a set of active matches that are currently occurring.
 */
public class MatchManager {
    private final ArrayList<Match> matches = new ArrayList<>();
    private final ArrayList<Minigame> minigames = new ArrayList<>();
    private final Queue queue;
    private final Logger log;

    /**
     * Creates a new match manager.
     * 
     * @param plugin - The BotDown plugin instance.
     */
    public MatchManager(BotDown plugin) {
        log = plugin.getLogger();

        queue = new Queue(log);

        var pluginManager = Bukkit.getPluginManager();
        var events = new MatchEvents(this);
        pluginManager.registerEvents(events, plugin);

        plugin.getCommand("match").setExecutor(new MatchCommand(plugin.getLang(), this));
    }

    /**
     * Creates a new, empty match.
     * 
     * @return The new match.
     */
    public Match createMatch() {
        var match = new Match();
        matches.add(match);

        log.info("Created new bot match " + match.getUUID());
        return match;
    }

    /**
     * Gets a list of all matches that the given player is currently in.
     * 
     * @param player - The player to look for.
     * @return A list of all matches the player is in.
     */
    public List<Match> findPlayer(Player player) {
        var list = new ArrayList<Match>();

        for (var match : matches) {
            if (match.getPlayers().contains(player))
                list.add(match);
        }

        return list;
    }

    /**
     * Ends the given match and removes it from the active match list.
     * 
     * @param match - The match to end.
     */
    public void endMatch(Match match) {
        match.end();
        matches.remove(match);
    }

    /**
     * Registers a new minigame type.
     * 
     * @param minigame - The minigame to register.
     */
    public void registerMinigame(Minigame minigame) {
        minigames.add(minigame);
        log.info("Registered minigame: " + minigame.getName());
    }

    /**
     * Finds a registered minigame with the given name. The name is not case
     * sensitive.
     * 
     * @param name - The name of the minigame.
     * @return The minigame, or null if no minigame could be found for the given
     *         name.
     */
    public Minigame findMinigame(String name) {
        for (var minigame : minigames)
            if (minigame.getName().equalsIgnoreCase(name))
                return minigame;

        return null;
    }

    /**
     * Adds a bot entry to the end of the match queue.
     * 
     * @param entry - The bot entry.
     */
    public void addBotToQueue(BotEntry entry) {
        queue.addEntry(entry);
    }
}
