package net.whg.util;

import java.io.InputStreamReader;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.janmm14.jsonmessagemaker.api.JsonMessageConverter;
import net.md_5.bungee.api.chat.BaseComponent;

/**
 * A string reference loader.
 */
public class Lang {
    private final FileConfiguration textConfig;

    public Lang(JavaPlugin plugin, String langFile) {
        var langResource = plugin.getResource(langFile);
        textConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(langResource));
    }

    public BaseComponent[] getRawMessage(String path, String locale) {
        var message = getMessageByLocale(path, locale);
        message = replaceLinks(message);
        message = replaceColors(message);

        return JsonMessageConverter.DEFAULT.convert(message);
    }

    private String replaceColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String replaceLinks(String message) {
        return message.replaceAll("\\!\\[(.*)\\]\\((.*)\\)", "[jmm|link=$2]$1[/jmm]");
    }

    private String getMessageByLocale(String path, String locale) {
        if (textConfig.contains(path + "." + locale, true))
            return textConfig.getString(path + "." + locale);

        // Default to english if translation doesn't exist.
        if (textConfig.contains(path + ".en_US", true))
            return textConfig.getString(path + ".en_US");

        throw new UnknownMessageException(path);
    }
}
