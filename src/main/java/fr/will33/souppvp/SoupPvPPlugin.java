package fr.will33.souppvp;

import fr.will33.souppvp.listener.PlayerListener;
import fr.will33.souppvp.manager.CommandManager;
import fr.will33.souppvp.manager.ConfigurationManager;
import fr.will33.souppvp.manager.PvPManager;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoupPvPPlugin extends JavaPlugin {

    private final Map<UUID, PvpPlayer> pvpPlayers = new HashMap<>();
    private ConfigurationManager configurationManager;
    private PvPManager pvPManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configurationManager = new ConfigurationManager();
        this.configurationManager.loadConfiguration(this.getConfig());

        this.pvPManager = new PvPManager();

        new CommandManager().registerCommands(this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    /**
     * Get {@link PvPManager} instance
     * @return
     */
    public PvPManager getPvPManager() {
        return pvPManager;
    }

    /**
     * Get {@link ConfigurationManager} instance
     * @return
     */
    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    /**
     * Get {@link PvpPlayer} lists
     * @return
     */
    public Map<UUID, PvpPlayer> getPvpPlayers() {
        return pvpPlayers;
    }

    /**
     * Get plugin instance
     * @return
     */
    public static SoupPvPPlugin getInstance() {
        return SoupPvPPlugin.getPlugin(SoupPvPPlugin.class);
    }
}
