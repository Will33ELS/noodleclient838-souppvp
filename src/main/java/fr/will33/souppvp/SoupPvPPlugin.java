package fr.will33.souppvp;

import fr.will33.souppvp.api.AbstractGUI;
import fr.will33.souppvp.api.ISQLBridge;
import fr.will33.souppvp.database.MySQLDatabase;
import fr.will33.souppvp.database.SQLLiteDatabase;
import fr.will33.souppvp.database.stockage.PlayerStockage;
import fr.will33.souppvp.listener.InventoryListener;
import fr.will33.souppvp.listener.PlayerListener;
import fr.will33.souppvp.manager.CommandManager;
import fr.will33.souppvp.manager.ConfigurationManager;
import fr.will33.souppvp.manager.KitManager;
import fr.will33.souppvp.manager.PvPManager;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.placeholder.CreditPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoupPvPPlugin extends JavaPlugin {

    private ISQLBridge sqlBridge;
    private final Map<UUID, PvpPlayer> pvpPlayers = new HashMap<>();
    private final Map<UUID, AbstractGUI> openGUI = new HashMap<>();
    private ConfigurationManager configurationManager;
    private PvPManager pvPManager;
    private FileConfiguration messagesConfig;
    private PlayerStockage playerStockage;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        if(this.getConfig().getBoolean("sqllite.enable")){
            this.sqlBridge = new SQLLiteDatabase(this.getConfig().getString("sqllite.filename"));
        }
        if(this.getConfig().getBoolean("mysql.enable") && this.sqlBridge == null){
            this.sqlBridge = new MySQLDatabase(this.getConfig().getString("mysql.host"),
                    this.getConfig().getString("mysql.database"),
                    this.getConfig().getString("mysql.username"),
                    this.getConfig().getString("mysql.password"),
                    this.getConfig().getString("mysql.prefixTable")
            );
        }
        if(this.sqlBridge == null){
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException("No database is configured!");
        }

        this.configurationManager = new ConfigurationManager();
        this.configurationManager.loadConfiguration(this.getConfig());

        File messagesFile = new File(this.getDataFolder(), "messages.yml");
        if (!messagesFile.exists())
            this.saveResource("messages.yml", false);
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        this.pvPManager = new PvPManager();
        this.playerStockage = new PlayerStockage();

        new KitManager().registerKits();
        new CommandManager().registerCommands(this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        try{
            new CreditPlaceholder().register();
        } catch (NoClassDefFoundError err){
            this.getLogger().info("No placeholder plugin detected");
        }
    }

    /**
     * Get all open gui
     * @return
     */
    public Map<UUID, AbstractGUI> getOpenGUI() {
        return openGUI;
    }


    /**
     * Open GUI
     * @param player Instance of the player
     * @param abstractGUI Instance of the GUI
     */
    public void openGUI(Player player, AbstractGUI abstractGUI){
        this.openGUI.put(player.getUniqueId(), abstractGUI);
        abstractGUI.onOpen(player);
    }

    /**
     * Get {@link PlayerStockage} instance
     * @return
     */
    public PlayerStockage getPlayerStockage() {
        return playerStockage;
    }

    /**
     * Get {@link ISQLBridge} instance
     * @return
     */
    public ISQLBridge getSqlBridge() {
        return sqlBridge;
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
