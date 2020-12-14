package net.whg.spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.whg.util.Lang;

public class SpawnHubManager implements Listener {
    private final Lang lang;

    public SpawnHubManager(Lang lang) {
        this.lang = lang;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        player.spigot().sendMessage(lang.getRawMessage("main.welcome", player.getLocale()));
    }
}
