package net.whg.match;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.util.Lang;

/**
 * Handler for the /match command.
 */
public class MatchCommand implements CommandExecutor {
    private final Lang lang;
    private final MatchManager matchManager;

    /**
     * Creates a new match command executor instance.
     * 
     * @param lang         - The language object to retrieve messages from.
     * @param matchManager - The match manager.
     */
    public MatchCommand(Lang lang, MatchManager matchManager) {
        this.lang = lang;
        this.matchManager = matchManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.spigot().sendMessage(lang.getRawMessage("command.mustBePlayer", null));
            return true;
        }

        var player = (Player) sender;
        var minigame = matchManager.findMinigame(args[0]);
        var botMaker = args.length == 2 ? args[1] : player.getName();

        if (minigame == null) {
            lang.sendMessage(player, "command.match.minigameNotFound", args[0]);
            return true;
        }

        var entry = new BotEntry(player, botMaker, minigame);
        matchManager.addBotToQueue(entry);

        lang.sendMessage(player, "command.match.addedToQueue", minigame.getName(), botMaker);

        return true;
    }
}
