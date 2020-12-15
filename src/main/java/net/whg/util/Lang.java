package net.whg.util;

import java.io.InputStreamReader;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.janmm14.jsonmessagemaker.api.JsonMessageConverter;
import net.md_5.bungee.api.chat.BaseComponent;

/**
 * A string reference resolver for all plugin translation messages.
 */
public class Lang {
    private final FileConfiguration textConfig;

    /**
     * Creates a new language reference resolver for the given plugin and language
     * resource file.
     */
    public Lang(JavaPlugin plugin, String langFile) {
        var langResource = plugin.getResource(langFile);
        textConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(langResource));
    }

    /**
     * Gets the raw message components to send to a player.
     * 
     * @param path   - The message path.
     * @param locale - The player's locale.
     * @param args   - The message formatting arguments.
     * @return The raw message component array.
     */
    public BaseComponent[] getRawMessage(String path, String locale, String... args) {
        var message = getMessageByLocale(path, locale);
        message = replaceArgs(message, args);
        message = replaceLinks(message);
        message = replaceColors(message);

        return JsonMessageConverter.DEFAULT.convert(message);
    }

    /**
     * Replaces the formatting arguments in the message with with the argument
     * values.
     * 
     * @param message - The message.
     * @param args    - The argument values.
     * @return The message with arguments inserted in.
     */
    private String replaceArgs(String message, String[] args) {
        for (int i = 0; i < args.length; i++)
            message = message.replace("${" + i + "}", args[i]);

        return message;
    }

    /**
     * Replaces all chat colors in the message with Minecraft supported chat colors.
     * 
     * @param message - The message.
     * @return The message with MC color formatting.
     */
    private String replaceColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Replaces all markdown-style links in the message with JsonMessageMaker-style
     * links.
     * 
     * @param message - The message.
     * @return The message with JMM link formatting.
     */
    private String replaceLinks(String message) {
        return message.replaceAll("\\!\\[(.*)\\]\\((.*)\\)", "[jmm|link=$2]$1[/jmm]");
    }

    /**
     * Gets the raw translation text for the given path and locale. Unknown locales
     * default to the en_US translation.
     * 
     * @param path   - The message path.
     * @param locale - The target locale.
     * @return The message in the given locale.
     * @throws UnknownMessageException If the message path does not exist in the
     *                                 translation file.
     */
    private String getMessageByLocale(String path, String locale) {
        if (textConfig.contains(path + "." + locale, true))
            return textConfig.getString(path + "." + locale);

        // Default to english if translation doesn't exist.
        if (textConfig.contains(path + ".en_US", true))
            return textConfig.getString(path + ".en_US");

        throw new UnknownMessageException(path);
    }

    /**
     * Sends the message at the given path to the player. The message respects the
     * player's locale preferences.
     * 
     * @param player - The player to send the message to.
     * @param path   - The message path in the translation file.
     * @param args   - The formatting arguments.
     */
    public void sendMessage(Player player, String path, String... args) {
        var message = getRawMessage(path, player.getLocale(), args);
        player.spigot().sendMessage(message);
    }
}
