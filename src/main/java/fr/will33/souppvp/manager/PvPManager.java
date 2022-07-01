package fr.will33.souppvp.manager;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.task.WaitingSpawnTask;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PvPManager {

    private final Map<Player, WaitingSpawnTask> waitingSpawns = new HashMap<>();

    /**
     * Teleport player in the spawn
     * @param player Instance of the player
     */
    public void teleportToSpawn(Player player){
        player.teleport(SoupPvPPlugin.getInstance().getConfigurationManager().getSpawnLocation());
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().setItem(0, SoupPvPPlugin.getInstance().getConfigurationManager().getBookItemStack());
    }

    /**
     * Retrieve players waiting to be teleported
     * @return
     */
    public Map<Player, WaitingSpawnTask> getWaitingSpawns() {
        return waitingSpawns;
    }
}
