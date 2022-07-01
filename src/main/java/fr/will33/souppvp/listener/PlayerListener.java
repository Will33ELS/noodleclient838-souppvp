package fr.will33.souppvp.listener;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PvpPlayer pvpPlayer = new PvpPlayer(player);
        SoupPvPPlugin.getInstance().getPvpPlayers().put(player.getUniqueId(), pvpPlayer);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        SoupPvPPlugin soupPvPPlugin = SoupPvPPlugin.getInstance();
        if(soupPvPPlugin.getPvPManager().getWaitingSpawns().containsKey(player)){
            soupPvPPlugin.getPvPManager().getWaitingSpawns().get(player).cancel();
            soupPvPPlugin.getPvPManager().getWaitingSpawns().remove(player);
        }
    }

}
