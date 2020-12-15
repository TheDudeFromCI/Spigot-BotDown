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
    private final MatchPositionIterator matchPositionIterator;
    private final ArenaBuilder arenaBuilder;
    private final Queue queue;
    private final Logger log;
    private final int arenaSize;

    /**
     * Creates a new match manager.
     * 
     * @param plugin - The BotDown plugin instance.
     */
    public MatchManager(BotDown plugin) {
        log = plugin.getLogger();
        var config = plugin.getConfig();

        queue = new Queue(this, log);
        arenaSize = config.getInt("match.size", 1000);

        matchPositionIterator = new MatchPositionIterator(arenaSize);
        arenaBuilder = new ArenaBuilder(plugin);

        var worldName = config.getString("world.name");
        var world = Bukkit.getWorld(worldName);

        var pluginManager = Bukkit.getPluginManager();
        var events = new MatchEvents(this, world);
        pluginManager.registerEvents(events, plugin);

        plugin.getCommand("match").setExecutor(new MatchCommand(plugin.getLang(), this));
    }

    /**
     * Creates a new, empty match.
     * 
     * @return The new match.
     */
    Match createMatch() {
        var matchPos = matchPositionIterator.next();

        var match = new Match(matchPos.getValue0(), matchPos.getValue1(), arenaSize);
        matches.add(match);

        arenaBuilder.buildArena(match);

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
     * @throws AlreadyInMatchException If the player is already in a match or in the
     *                                 queue.
     */
    public void addBotToQueue(BotEntry entry) {
        var inMatch = matches.stream().anyMatch(m -> m.getPlayers().contains(entry.getBot()));
        if (inMatch)
            throw new AlreadyInMatchException(entry.getBot(), false);

        queue.addEntry(entry);
    }

    /**
     * Removes the given player from all matches they are currently in.
     * 
     * @param player - The player to remove.
     */
    public void removeFromAllMatches(Player player) {
        for (var match : findPlayer(player)) {
            match.removePlayer(player);
        }
    }

    /**
     * Removes the given player from the queue if they are currently in it.
     * 
     * @param player - The player to remove.
     */
    public void removeFromQueue(Player player) {
        queue.removePlayer(player);
    }

    /**
     * Gets the size value, in blocks, of match arenas in the world.
     * 
     * @return The arena size in blocks.
     */
    public int getArenaSize() {
        return arenaSize;
    }
}
