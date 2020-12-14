package net.whg.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 * Stops the world from being saved.
 */
public class PreventSaveEvent implements Listener {

    /**
     * Listens for when chunks are unloaded and stops them from being saved.
     */
    @EventHandler
    public void onChunkUnloaded(ChunkUnloadEvent event) {
        event.setSaveChunk(false);
    }
}
