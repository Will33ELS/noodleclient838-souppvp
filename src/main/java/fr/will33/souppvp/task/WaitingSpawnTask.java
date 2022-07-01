package fr.will33.souppvp.task;

import fr.will33.souppvp.SoupPvPPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingSpawnTask extends BukkitRunnable {

    private final Player player;
    public WaitingSpawnTask(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        if(this.player == null) return;
        SoupPvPPlugin.getInstance().getPvPManager().teleportToSpawn(this.player);
        SoupPvPPlugin.getInstance().getPvPManager().getWaitingSpawns().remove(this.player);
    }
}
