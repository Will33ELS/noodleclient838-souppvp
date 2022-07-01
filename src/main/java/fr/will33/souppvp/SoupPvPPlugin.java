package fr.will33.souppvp;

import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoupPvPPlugin extends JavaPlugin {

    private final Map<UUID, PvpPlayer> pvpPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
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
