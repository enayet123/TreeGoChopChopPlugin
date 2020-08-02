package treegochopchop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class called by Bukkit/Spigot/Paper to initialize
 * plugin
 */
public class TreeGoChopChopPlugin extends JavaPlugin {

    /**
     * Registers events from TreeGoChopChop when
     * plugin has successfully initialized
     */
    @Override
    public void onEnable() {
        // State plugin is active
        Bukkit.getLogger().info(Constants.STATUS_ACTIVATED);

        // Register listeners
        this.getServer().getPluginManager().registerEvents(new TreeGoChopChopEvents(), this);

    }

    /**
     * Announces deactivation when plugin is disabled
     */
    @Override
    public void onDisable() { Bukkit.getLogger().info(Constants.STATUS_DEACTIVATED); }

}
