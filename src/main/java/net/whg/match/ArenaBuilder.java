package net.whg.match;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import net.whg.BotDown;

/**
 * An async builder that constructs new arenas as needed without causing lag on
 * the main thread.
 */
public class ArenaBuilder {
    private final BotDown plugin;
    private final World world;
    private final int blocksPerTick;

    /**
     * Creates a new arena builder.
     * 
     * @param plugin - The BotDown plugin instance.
     */
    public ArenaBuilder(BotDown plugin) {
        this.plugin = plugin;

        var config = plugin.getConfig();
        var worldName = config.getString("world.name");
        world = Bukkit.getWorld(worldName);

        blocksPerTick = config.getInt("match.buildSpeed");
    }

    /**
     * Starts an async operation for building the given match arena.
     * 
     * @param match - The match arena.
     */
    void buildArena(Match match) {
        var instance = new BuildInstance(match);
        var task = Bukkit.getScheduler().runTaskTimer(plugin, instance, 1, 1);
        instance.setTask(task);
    }

    /**
     * A single build instance for the given match.
     */
    private class BuildInstance implements Runnable {
        private final Match match;
        private BukkitTask task;
        private int x;
        private int z;

        /**
         * Creates a new build instance.
         * 
         * @param match - The match this builder should build for.
         */
        BuildInstance(Match match) {
            this.match = match;
            x = match.x();
            z = match.z();
        }

        /**
         * Sets the internal task so that the task might be canceled when complete.
         * 
         * @param task - The async task for canceling.
         */
        void setTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            for (int i = 0; i < blocksPerTick; i++) {
                placeNextBlock();
                if (isDone()) {
                    cleanup();
                    return;
                }
            }
        }

        /**
         * Cleanup this async task instance.
         */
        private void cleanup() {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
        }

        /**
         * Checks if the task is done or not.
         * 
         * @return True if the task is done. False otherwise.
         */
        private boolean isDone() {
            return z == match.z() + match.size();
        }

        /**
         * Places the next block for this match and updates the internal block
         * increment.
         */
        private void placeNextBlock() {
            world.getBlockAt(x, 64, z).setType(Material.COBBLESTONE);

            x++;
            if (x == match.x() + match.size()) {
                x = match.x();
                z++;
            }
        }
    }
}
