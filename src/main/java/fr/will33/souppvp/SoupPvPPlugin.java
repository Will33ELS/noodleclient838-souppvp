package fr.will33.souppvp;

import fr.will33.souppvp.listener.PlayerListener;
import fr.will33.souppvp.manager.CommandManager;
import fr.will33.souppvp.manager.ConfigurationManager;
import fr.will33.souppvp.manager.PvPManager;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoupPvPPlugin extends JavaPlugin {

    private final Map<UUID, PvpPlayer> pvpPlayers = new HashMap<>();
    private ConfigurationManager configurationManager;
    private PvPManager pvPManager;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configurationManager = new ConfigurationManager();
        this.configurationManager.loadConfiguration(this.getConfig());

        File messagesFile = new File(this.getDataFolder(), "messages.yml");
        if (!messagesFile.exists())
            this.saveResource("messages.yml", false);
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        this.pvPManager = new PvPManager();

        new CommandManager().registerCommands(this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    /**
     * Get messages.yml instance
     * @return
     */
    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
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
